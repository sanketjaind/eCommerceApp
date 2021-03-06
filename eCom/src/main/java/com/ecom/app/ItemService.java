package com.ecom.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository repo;
	
	public List<Item> listAll() {
		return repo.findAll();
	}
	
	public void save(Item item) {
		repo.save(item);
	}
	
	public Item find(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}

	public Status orderItem(Order orderedItem) {
		Status msg = new Status();
		Item orderItem = repo.findById(orderedItem.getId()).get();
		int isItemInStoke = orderItem.getTotalStoke() - orderedItem.getTotalNoOfOrder();
		if(isItemInStoke >= 0){
			orderItem.setTotalStoke(orderItem.getTotalStoke() - orderedItem.getTotalNoOfOrder());
			repo.save(orderItem);
			msg.setStatus("Sucess");
			msg.setMsg("Order Sucessful");
		}else {
			msg.setStatus("Fail");
			msg.setMsg("No Stock Available you can order max : " + orderItem.getTotalStoke() + " items");
		}
		
		return msg;
		
	}
}
