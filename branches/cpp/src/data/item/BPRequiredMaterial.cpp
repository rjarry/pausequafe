/*****************************************************************************
 * This file is part of Pause Quafe.                                         *
 *                                                                           *
 * Pause Quafe - An Eve-Online(TM) character assistance application          *
 * Copyright (c) 2009  diabeteman & Kios Askoner                             *
 *                                                                           *
 * Pause Quafe is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafe is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafe.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

#include "BPRequiredMaterial.h"

BPRequiredMaterial::BPRequiredMaterial(uint typeID, uint quantity, double damagePerJob) :
        typeID(typeID),
        quantity(quantity),
        damagePerJob(damagePerJob)
{
}

BPRequiredMaterial::BPRequiredMaterial(const BPRequiredMaterial & mat) :
    typeID(mat.typeID),
    quantity(mat.quantity),
    damagePerJob(mat.damagePerJob)
{
}

double BPRequiredMaterial::getDamagePerJob() const {
    return damagePerJob;
}

uint BPRequiredMaterial::getQuantity() const {
    return quantity;
}

uint BPRequiredMaterial::getTypeID() const {
    return typeID;
}

void BPRequiredMaterial::setDamagePerJob(double damagePerJob) {
    this->damagePerJob = damagePerJob;
}

void BPRequiredMaterial::setQuantity(uint quantity) {
    this->quantity = quantity;
}

void BPRequiredMaterial::setTypeID(uint typeID) {
    this->typeID = typeID;
}
