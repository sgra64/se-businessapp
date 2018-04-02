package com.businessapp.logic;

import java.util.Collection;
import java.util.HashMap;

import com.businessapp.ControllerIntf;
import com.businessapp.pojos.Customer;


/**
 * Implementation of Customer data access methods.
 *
 */
class CustomerDataMockImpl implements CustomerDataIntf {
	private final HashMap<String,Customer> _data;
	private final CustomerDataIntf DS;				// represents Data Source / Data Store


	CustomerDataMockImpl() {
		this._data = new HashMap<String,Customer>();
		this.DS = this;
	}

	@Override
	public void inject( ControllerIntf dep ) {
	}

	@Override
	public void start() {
		Customer eric = DS.newCustomer( "Eric" ).addContact( "eric@gmail.com" );
		Customer anja = DS.newCustomer( "Anja");
		DS.newCustomer( "Moritz" );
		DS.newCustomer( "Claudia" );
		eric.addContact( "e532@yahoo.com" );
		anja.addContact( "Berlin" );
	}

	@Override
	public void stop() {
	}

	@Override
	public Customer findCustomerById( String id ) {
		return _data.get( id );
	}

	@Override
	public Collection<Customer> findAllCustomers() {
		return _data.values();
	}

	@Override
	public Customer newCustomer( String name ) {
		Customer e = new Customer( null, name );
		_data.put( e.getId(), e );
		//save( "created: ", c );
		return e;
	}

	@Override
	public void updateCustomer( Customer e ) {
		String msg = "updated: ";
		if( e != null ) {
			Customer e2 = _data.get( e.getId() );
			if( e != e2 ) {
				if( e2 != null ) {
					_data.remove( e2.getId() );
				}
				msg = "created: ";
				_data.put( e.getId(), e );
			}
			//save( msg, c );
			System.err.println( msg + e.getId() );
		}
	}

	@Override
	public void deleteCustomers( Collection<String> ids ) {
		String showids = "";
		for( String id : ids ) {
			_data.remove( id );
			showids += ( showids.length()==0? "" : ", " ) + id;
		}
		if( ids.size() > 0 ) {
			//save( "deleted: " + idx, customers );
			System.err.println( "deleted: " + showids );
		}
	}

}
