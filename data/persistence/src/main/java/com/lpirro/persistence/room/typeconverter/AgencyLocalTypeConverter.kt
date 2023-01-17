/*
 *
 * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 * Copyright (C) 2023 Leonardo Pirro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.lpirro.persistence.room.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lpirro.persistence.model.AgencyLocal

@ProvidedTypeConverter
class AgencyLocalTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun agencyLocalToString(agencyLocal: AgencyLocal): String {
        return gson.toJson(agencyLocal)
    }

    @TypeConverter
    fun stringToAgencyLocal(agencyLocalString: String): AgencyLocal {
        val objectType = object : TypeToken<AgencyLocal>() {}.type
        return gson.fromJson(agencyLocalString, objectType)
    }
}
