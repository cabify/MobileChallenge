//
//  HomeViewControllerDelegateExtension.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 12/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation


import UIKit

extension HomeViewController: UITableViewDelegate {

    private func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
            return UITableView.automaticDimension
        }
}
