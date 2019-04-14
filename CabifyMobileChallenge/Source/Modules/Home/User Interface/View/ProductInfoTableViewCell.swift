//
//  ProductInfoTableViewCell.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 10/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import UIKit

class ProductInfoTableViewCell: UITableViewCell {
    
    public static let ID = "ProductInfoTableViewCell"
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var codeLabel: UILabel!
    @IBOutlet weak var priceLabel: UILabel!
    
//    private var disposeBag = DisposeBag()
    
    func configure(with item: Product) {
        nameLabel?.text    = item.name
        codeLabel?.text    = item.code
        priceLabel?.text   = String(item.price)
//        button.rx.action = action
//
//        item.rx.observe(String.self, "title")
//            .subscribe(onNext: { [weak self] title in
//                self?.title.text = title
//            })
//            .disposed(by: disposeBag)
        
//        item.rx.observe(Date.self, "checked")
//            .subscribe(onNext: { [weak self] date in
//                let image = UIImage(named: date == nil ? "ItemNotChecked" : "ItemChecked")
//                self?.button.setImage(image, for: .normal)
//            })
//            .disposed(by: disposeBag)
    }
    
//    override func prepareForReuse() {
//        button.rx.action = nil
//        disposeBag = DisposeBag()
//        super.prepareForReuse()
//    }
    
    func updateProduct(_ product: Product){
        nameLabel.text    = product.name
        codeLabel.text    = product.code
        priceLabel.text   = String(product.price)
    }
}
