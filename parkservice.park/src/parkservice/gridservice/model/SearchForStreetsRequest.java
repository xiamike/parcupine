package parkservice.gridservice.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class SearchForStreetsRequest {
	private List<SearchArea> searchArea;

	/**
	 * Get the search area
	 * @return
	 */
	public List<SearchArea> getSearchArea() {
		return searchArea;
	}

	/**
	 * set the search area list
	 * @param searchArea
	 */
	public void setSearchArea(List<SearchArea> searchArea) {
		this.searchArea = searchArea;
	}
}
