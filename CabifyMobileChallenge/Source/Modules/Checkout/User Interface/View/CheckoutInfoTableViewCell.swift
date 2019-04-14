//
//  CheckoutInfoTableViewCell.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 10/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import UIKit

class CheckoutInfoTableViewCell: UITableViewCell {
    
    public static let ID = "CheckoutInfoTableViewCell"
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var countLabel: UILabel!
    @IBOutlet weak var priceLabel: UILabel!
    
    func configure(with item: Checkout) {
        nameLabel?.text    = item.name
        countLabel?.text   = "x " + String(item.count)
        priceLabel?.text   = String(item.totalPrice)
    }
}
