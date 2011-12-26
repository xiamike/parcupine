package parkservice.resources;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBElement;

import parkservice.model.EditUserRequest;
import parkservice.model.EditUserResponse;
import parkservice.model.RegisterRequest;
import parkservice.model.RegisterResponse;

import AuthNet.Rebill.ArrayOfCustomerPaymentProfileType;
import AuthNet.Rebill.CreateCustomerProfileResponseType;
import AuthNet.Rebill.CreditCardType;
import AuthNet.Rebill.CustomerAddressType;
import AuthNet.Rebill.CustomerPaymentProfileType;
import AuthNet.Rebill.CustomerProfileType;
import AuthNet.Rebill.PaymentType;
import AuthNet.Rebill.ServiceSoap;
import AuthNet.Rebill.ValidationModeEnum;

import com.parq.server.dao.PaymentAccountDao;
import com.parq.server.dao.UserDao;
import com.parq.server.dao.exception.DuplicateEmailException;
import com.parq.server.dao.model.object.PaymentAccount;
import com.parq.server.dao.model.object.User;



@Path("/")
public class RegisterResource {
	@Context 
	ContextResolver<JAXBContextResolver> providers;

	private CustomerProfileType createUserProfile(long uid, String email, String desc){
		CustomerProfileType xx = new CustomerProfileType();
		xx.setDescription(desc);
		xx.setEmail(email);
		xx.setMerchantCustomerId(""+uid);
		return xx;
	}
	
	/* CustomerProfileType.setShipToList(list of address types)
	may be needed as a billing address.  	
	
	billTo	CustomerAddressType may be needed.  
	*/

	private CreateCustomerProfileResponseType validateCard(CustomerProfileType customer, String ccNum, String csc, int month, int year, 
			String fname, String lname, String zipcode, String address){
		try{
			
			

			ServiceSoap soap = SoapAPIUtilities.getServiceSoap();
			CustomerPaymentProfileType new_payment_profile = new CustomerPaymentProfileType();
			
			PaymentType new_payment = new PaymentType();
			CreditCardType new_card = new CreditCardType();
			new_card.setCardNumber(ccNum);
			new_card.setCardCode(csc);
			try{
				javax.xml.datatype.XMLGregorianCalendar cal = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar();
				cal.setMonth(month);
				cal.setYear(year);
				new_card.setExpirationDate(cal);
			}
			catch(javax.xml.datatype.DatatypeConfigurationException dce){
				return null;
			}

			new_payment.setCreditCard(new_card);
			new_payment_profile.setPayment(new_payment);
			
			CustomerAddressType billToAddr = new CustomerAddressType();
			
			billToAddr.setAddress(address);
			//billToAddr.setCity("Wilmington");
			//billToAddr.setState("DE");
			//billToAddr.setPhoneNumber("3023546447");
			billToAddr.setZip(zipcode);
			billToAddr.setFirstName(fname);
			billToAddr.setLastName(lname);
			new_payment_profile.setBillTo(billToAddr);
			
			ArrayOfCustomerPaymentProfileType pay_list = new ArrayOfCustomerPaymentProfileType();
			pay_list.getCustomerPaymentProfileType().add(new_payment_profile);

			customer.setPaymentProfiles(pay_list);

			return soap.createCustomerProfile(SoapAPIUtilities.getMerchantAuthentication(),customer
					,ValidationModeEnum.LIVE_MODE);
		}catch(Exception e){
			return null;
		}

	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RegisterResponse register(JAXBElement<RegisterRequest> input){
		RegisterRequest info = input.getValue();
		User newUser = new User();
		newUser.setEmail(info.getEmail());
		newUser.setPassword(info.getPassword());
		UserDao userDb = new UserDao();


		RegisterResponse output = new RegisterResponse();
		boolean result = false;
		try{
			//try to create user account, catching errors.  
			result = userDb.createNewUser(newUser);
		}catch(DuplicateEmailException dup){
			output.setResp("USER_EXISTS");
		}catch(IllegalStateException e){
			output.setResp("DAO_ERROR");
		}
		String email = info.getEmail();
		long uid = userDb.getUserByEmail(email).getUserID();
		String description = "UID:" + uid +" Email:" +email;
		CustomerProfileType newCustomer = createUserProfile(uid, info.getEmail(), description);

		List<String> nameSplit = Arrays.asList(info.getHolderName().split(" "));
		String fname = null;
		String lname = null;
		if(nameSplit.size()>1){
			fname = nameSplit.get(0);
			lname = nameSplit.get(nameSplit.size()-1);
		}
		if(result && lname != null && fname != null){
			CreateCustomerProfileResponseType response = validateCard(newCustomer, 
					info.getCreditCard(), info.getCscNumber(), info.getExpMonth(), info.getExpYear(),
					fname, lname, info.getZipcode(), info.getAddress());


			if(response.getResultCode().value().equalsIgnoreCase("Ok")){
				long profileId = response.getCustomerProfileId();
				List<Long> test = response.getCustomerPaymentProfileIdList().getLong();
				long paymentProfileId = test.get(0);
				PaymentAccountDao pad = new PaymentAccountDao();

				PaymentAccount newPA = new PaymentAccount();
				if(info.getCreditCard().length()==16){
					newPA.setCcStub(info.getCreditCard().substring(12, 16));
					newPA.setCustomerId(""+profileId);
					newPA.setDefaultPaymentMethod(true);
					newPA.setPaymentMethodId(""+paymentProfileId);
					newPA.setUserId(uid);
				}else{
					userDb.deleteUserById(uid);
					output.setResp("BAD_CC");
				}
				boolean paCreationSuccessful = pad.createNewPaymentMethod(newPA);
				if(paCreationSuccessful){
					output.setResp("OK");
				}else{
					//payment account creation error
					userDb.deleteUserById(uid);
					output.setResp("PAY_ACC_ERROR");
				}
			}else{
				//cc didn't verify
				userDb.deleteUserById(uid);
				output.setResp("BAD_CC");
			}

		}else{
			//result is false
			output.setResp("SERVER_ERROR");
		}
		return output;
	}


	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EditUserResponse parkUser(JAXBElement<EditUserRequest> info){
		EditUserResponse output = new EditUserResponse();
		EditUserRequest in = info.getValue();
		UserDao userDb = new UserDao();
		User editedUser = new User();
		editedUser.setEmail(in.getEmail());
		editedUser.setPassword(in.getPassword());
		editedUser.setPhoneNumber(in.getPhone());
		editedUser.setUserID(in.getUid());

		boolean result = true;
		try{
			result = userDb.updateUser(editedUser);
		}catch(IllegalStateException ex){
			output.setResp("illegal state");
		}catch(RuntimeException e){
			output.setResp("runtime exception");
		}
		if(result){
			output.setResp("OK");
		}else{
			output.setResp("BAD");
		}
		return output;
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "goin shopping with YOUR credit card";
	}
}