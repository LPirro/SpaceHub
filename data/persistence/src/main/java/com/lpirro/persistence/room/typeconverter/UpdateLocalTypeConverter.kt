/*
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
 */

package com.lpirro.persistence.room.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lpirro.persistence.model.UpdateLocal

@ProvidedTypeConverter
class UpdateLocalTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun updateLocalListToString(updates: List<UpdateLocal>?): String? {
        val type = object : TypeToken<List<UpdateLocal>>() {}.type
        return gson.toJson(updates, type)
    }

    @TypeConverter
    fun stringToUpdateLocalList(updateString: String?): List<UpdateLocal>? {
        val objectType = object : TypeToken<List<UpdateLocal>>() {}.type
        return gson.fromJson(updateString, objectType)
    }
}
