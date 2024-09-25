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
package com.lpirro.spacehub.launches.data.mapper

import com.lpirro.spacehub.launches.data.network.model.UpdateRemote
import com.lpirro.spacehub.launches.domain.model.Update

interface UpdateMapper {
    fun mapToDomain(updateRemote: UpdateRemote): Update
}

class UpdateMapperImpl(private val dateParser: DateParser) : UpdateMapper {
    override fun mapToDomain(updateRemote: UpdateRemote) =
        Update(
            id = updateRemote.id,
            profileImage = updateRemote.profileImage,
            comment = updateRemote.comment,
            createdOn = updateRemote.createdOn,
            createdBy = updateRemote.createdBy,
            infoUrl = updateRemote.infoUrl,
        )
}
