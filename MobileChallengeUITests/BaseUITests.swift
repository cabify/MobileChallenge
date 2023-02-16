//
//  BaseUITests.swift
//  MobileChallengeUITests
//
//  Created by thomas pereira on 16/02/2023.
//

import XCTest

class BaseUITests: XCTestCase {
    
    static private(set) var launched = false
    private(set) var app = XCUIApplication()
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        if !BaseUITests.launched {
            self.app.terminate()
            
            self.app.launchArguments = ["-StartFromCleanState", "YES", "IS_UI_TESTING"]
            self.app.launch()
            
            BaseUITests.launched = true
        }
        
        continueAfterFailure = false
    }
}
