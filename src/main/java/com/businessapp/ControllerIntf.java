package com.businessapp;


public interface ControllerIntf {

	public void inject( ControllerIntf dep );

	public void start();

	public void stop();

}
