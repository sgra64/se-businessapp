package com.businessapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.businessapp.fxgui.AppGUIBuilder;
import com.businessapp.logic.CalculatorLogicIntf;
import com.businessapp.logic.CustomerDataIntf;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class launched by JavaFX runtime.
 *
 */
public class App extends Application {
	public static final String NAME			= "SE Business App";
	public static final String DATA_PATH	= "data/";
	public static final String FXML_PATH	= "fxgui/";
	private static App _app = null;

	/*
	 * List of App components in order of appearance on the main GUI/TabPanel.
	 */
	private List<Component> components = Arrays.asList( new Component[] {
		new Component(	"Main",			"App.fxml",			null ),
		new Component(	"Calculator",	"Calculator.fxml",	CalculatorLogicIntf.getController() ),
		new Component(	"Calc_2",		"Calculator.fxml",	CalculatorLogicIntf.getController() ),
		new Component(	"Kunden",		"Customer.fxml",	CustomerDataIntf.getController() ),
		new Component(	"Kundenliste_B","Customer.fxml",	CustomerDataIntf.getController() ),
		//new Component( "Katalog",		"Catalog.fxml",		CatalogDataIntf.getController() ),
	});

	public static App getInstance() {
		return _app;
	}

	public static void main( String[] args) {
		launch( args );
	}

	@Override
	public void start( Stage stage ) throws IOException {
		App._app = this;
		AppGUIBuilder appGUIBuilder = AppGUIBuilder.getInstance();

		System.out.print( " **** Building Scene: " );

		appGUIBuilder.buildAppUI( stage, components, ( tab, comp, fxmlController ) -> {
			comp.inject( fxmlController );
			System.out.print( ", " + comp.getName() );
		});
		System.out.println( " OK." );

		startComponentsWrapper( "    + Starting Controllers: ", 1, comp -> {
			comp.start();
		});

		appGUIBuilder.getTabPane().getSelectionModel().select( 2 );		// select n-th Tab
		stage.setTitle( App.NAME );
		stage.show();		// show JavaFX GUI
	}

	@Override
	public void stop() {
		System.out.print( " **** Shutting down ..." );
		stopComponentsWrapper( "\n    + Stopping Controllers: ", 1, comp -> {
			comp.stop();
		});
		System.out.println( " OK." );
		System.exit( 0 );
	}


	/*
	 * *********************************************************************
	 * Private helper methods.
	 */
	@FunctionalInterface
	private interface ControllerCallbackIntf {
		void call( ControllerIntf controller );
	}

	private void startComponentsWrapper( String logMsg, int logLevel, ControllerCallbackIntf callBack ) {
		boolean first = true;
		log( logMsg, logLevel );
		for( Component c : components ) {
			if( c.getLogic() != null ) {
				callBack.call( c.getLogic() );
				first = log( first, ( ( logLevel & 1 ) == 0 )? null : c.getLogic() );
			}
		}
		for( Component c : components ) {
			if( c.getGUIController() != null ) {
				callBack.call( c.getGUIController() );
				first = log( first, ( ( logLevel & 2 ) == 0 )? null: c.getGUIController() );
			}
		}
		log( " OK.\n", logLevel );
	}

	private void stopComponentsWrapper( String logMsg, int logLevel, ControllerCallbackIntf callBack ) {
		boolean first = true;
		Collections.reverse( components );
		log( logMsg, logLevel );
		for( Component c : components ) {
			callBack.call( c.getGUIController() );
			first = log( first, ( ( logLevel & 2 ) == 0 )? null: c.getGUIController() );
		}
		for( Component c : components ) {
			callBack.call( c.getLogic() );
			first = log( first, ( ( logLevel & 1 ) == 0 )? null : c.getLogic() );
		}
	}

	private boolean log( boolean first, ControllerIntf controller ) {
		boolean res = first;
		if( controller != null ) {
			res = false;
			String comma = first? "" : ", ";
			System.out.print( comma + controller.getClass().getSimpleName() );
			annimateDelay( 80 );
		}
		return res;
	}

	private void log( String logLine, int logLevel ) {
		if( logLevel > 0 ) {
			annimateDelay( 120 );
			System.out.print( logLine );
		}
	}

	private void annimateDelay( int millis ) {
		try { TimeUnit.MILLISECONDS.sleep( millis ); } catch( InterruptedException e ) { }
	}

}
