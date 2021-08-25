package rs.ac.uns.ftn.informatika.spring.security.model;

import java.util.Comparator;

public class PriceListSort implements Comparator<PriceList>{
	
	private int order = -1;
	
	public PriceListSort(int order) {
		super();
		this.order = order;
	}

	@Override
	public int compare(PriceList arg0, PriceList arg1) {
		// TODO Auto-generated method stub
		return order * arg0.getStartDate().compareTo(arg1.getStartDate());
	}

}
