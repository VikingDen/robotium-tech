/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

package com.robotium.test;

import android.app.Instrumentation;
import android.support.test.uiautomator.UiDevice;
import com.robotium.solo.TSolo;
import com.robotium.solo.tcl.runner.TActivityInstrumentationTestCase2;
import com.tcl.uiautomator.core.USolo;


public class NotePadTest extends TActivityInstrumentationTestCase2 {
    private static final String MAIN_ACTIVITY_NAME 	= "com.example.android.notepad.NotesList" ;
    private TSolo solo;
    private static Class<?> activityClass  ;
    private UiDevice mDevice;

    private USolo uSolo ;

    static {
        try {
            activityClass = Class.forName(MAIN_ACTIVITY_NAME) ;
        } catch (ClassNotFoundException e) { throw new RuntimeException("MainActivity class load has failed...") ;}
    }

    @SuppressWarnings("unchecked")
    public NotePadTest() {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = getSolo();
        Instrumentation instrumentation = getInstrumentation() ;
        mDevice = UiDevice.getInstance(instrumentation);
        uSolo = USolo.getInstance(mDevice) ;
    }

    public void testAddNote() throws Exception {
        //Robotium code : add a new note
        //Unlock the lock screen
        solo.unlockScreen();
        solo.clickOnMenuItem("Add note");
		//Assert that NoteEditor activity is opened
		solo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor"); 
		//In text field 0, enter Note 1
		solo.enterText(0, "Note 1");
		solo.goBack(); 

		//Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
		solo.takeScreenshot();
		boolean notesFound = solo.searchText("Note 1") ;
		//Assert that Note 1 & Note 2 are found
		assertTrue("Note 1 are not found", notesFound);
        solo.sleep(5000) ;

        //UiAutomator Code :
        //use UiDevice to open notification
        mDevice.openNotification() ;
        //use UiDevice to go back
        solo.sleep(3000);
        mDevice.pressBack() ;
        solo.sleep(3000);

        //use UiDevice get current package name
        String expected_package_name = "com.example.android.notepad" ;
        String package_name = mDevice.getCurrentPackageName() ;
        assertEquals("Current active package name is not " + expected_package_name , expected_package_name , package_name);
        solo.sleep(3000) ;

        uSolo.clickOnMenuItem("Add note");
        solo.sleep(5000) ;

        //Robotium code
        //Assert that NoteEditor activity is opened
        solo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor");
        //In text field 0, enter Note 1
        solo.enterText(0, "Note 2");
        solo.goBack();

        //Assert that Note 1 & Note 2 are found
        assertTrue("Note 1 & Note 2 are not found", solo.searchText("Note 1") && solo.searchText("Note 2") );
    }
}
