package org.extractor;

import org.extractor.exceptions.ExtractionException;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Christian Schabesberger on 12.02.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.org>
 * InfoItemCollector.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

public abstract class InfoItemCollector {
    private List<InfoItem> itemList = new ArrayList<>();
    private List<Throwable> errors = new ArrayList<>();
    private int serviceId = -1;

    public InfoItemCollector(int serviceId) {
        this.serviceId = serviceId;
    }

    public List<InfoItem> getItemList() {
        return itemList;
    }

    public List<Throwable> getErrors() {
        return errors;
    }

    protected void addFromCollector(InfoItemCollector otherC) throws ExtractionException {
        if (serviceId != otherC.serviceId) {
            throw new ExtractionException("Service Id does not equal: "
                    + NewPipe.getNameOfService(serviceId)
                    + " and " + NewPipe.getNameOfService((otherC.serviceId)));
        }
        errors.addAll(otherC.errors);
        itemList.addAll(otherC.itemList);
    }

    protected void addError(Exception e) {
        errors.add(e);
    }

    protected void addItem(InfoItem item) {
        itemList.add(item);
    }

    protected int getServiceId() {
        return serviceId;
    }
}
