package com.crm.guard.service.delivery.fabric;

import com.crm.guard.service.delivery.sender.DeliverySender;

public interface DeliveryStrategyFabric {

    DeliverySender deliveryGuy(String type);

}
