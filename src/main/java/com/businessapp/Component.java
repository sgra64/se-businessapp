package com.businessapp;

/**
 * A Component is a higher-level system abstraction that typically consists of
 * multiple objects. A Component here has a:
 * - has a name,
 * - has a GUI/presentation with a
 *   - Tab on the main Tab-panel with content obtained from a .fxml resource,
 *   - FXML controller that handles user interaction and presentation events.
 * 
 * Furthermore, a component:
 * - has separate logic that is isolated from the GUI. 
 * 
 */
public class Component {
	private final ControllerIntf dummyCtrl = new DummyController();
	private final String name;
	private final String fxmlResource;
	private ControllerIntf fxmlController;
	private final ControllerIntf logic;

	class DummyController implements ControllerIntf {
		@Override
		public void inject( ControllerIntf dep ) { }

		@Override
		public void start() { }

		@Override
		public void stop() { }
	}

	Component( String name, String fxmlResource, ControllerIntf logic ) {
		this.name = name;
		this.fxmlResource = fxmlResource;
		this.fxmlController = dummyCtrl;
		this.logic = logic != null? logic : dummyCtrl;
	}

	public String getName() {
		return name;
	}

	public String getFxmlResource() {
		return fxmlResource;
	}

	public ControllerIntf getLogic() {
		return logic;
	}

	public ControllerIntf getGUIController() {
		return fxmlController;
	}

	public void inject( ControllerIntf fxmlController ) {
		this.fxmlController = fxmlController;
		logic.inject( fxmlController );
		fxmlController.inject( logic );
	}

}
