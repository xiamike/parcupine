//
//  PQParkingViewController.h
//  Parq
//
//  Created by Mark Yen on 4/26/12.
//  Copyright (c) 2012 Massachusetts Institute of Technology. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PQParkingViewController : UITableViewController <UITableViewDelegate>
@property (nonatomic, readonly) double rate;
@property (nonatomic) int rateNumeratorCents;
@property (nonatomic) int rateDenominatorMinutes;
@property (nonatomic) int limit;
@property (nonatomic, copy) NSString *address;
@property (weak, nonatomic) IBOutlet UIButton *startButton;
@property (weak, nonatomic) IBOutlet UIButton *unparkButton;
@property (weak, nonatomic) IBOutlet UIImageView *paygFlag;
@property (weak, nonatomic) IBOutlet UIImageView *prepaidFlag;
@property (weak, nonatomic) IBOutlet UILabel *hours;
@property (weak, nonatomic) IBOutlet UILabel *minutes;
@property (weak, nonatomic) IBOutlet UIImageView *paygCheck;
@property (weak, nonatomic) IBOutlet UIImageView *prepaidCheck;
@property (weak, nonatomic) IBOutlet UILabel *prepaidAmount;
@property (weak, nonatomic) IBOutlet UIView *paygView;
@property (weak, nonatomic) IBOutlet UIView *addressView;
@property (weak, nonatomic) IBOutlet UIView *prepaidView;
@property (weak, nonatomic) IBOutlet UIView *seeMapView;
@property (weak, nonatomic) IBOutlet UIDatePicker *datePicker;
@property (weak, nonatomic) IBOutlet UILabel *rateNumerator;
@property (weak, nonatomic) IBOutlet UILabel *rateDenominator;
@property (weak, nonatomic) IBOutlet UILabel *limitValue;
@property (weak, nonatomic) IBOutlet UILabel *limitUnit;
@property (weak, nonatomic) IBOutlet UILabel *addressLabel;

@end