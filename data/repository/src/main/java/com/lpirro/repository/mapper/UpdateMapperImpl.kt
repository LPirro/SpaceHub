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

package com.lpirro.repository.mapper

import com.lpirro.domain.models.Update
import com.lpirro.network.models.UpdateRemote
import com.lpirro.persistence.model.UpdateLocal

class UpdateMapperImpl(private val dateParser: DateParser) : UpdateMapper {
    override fun mapToDomain(updateLocal: UpdateLocal) = Update(
        id = updateLocal.id,
        profileImage = updateLocal.profileImage,
        comment = updateLocal.comment,
        createdOn = updateLocal.createdOn,
        createdBy = updateLocal.createdBy,
        infoUrl = updateLocal.infoUrl
    )

    override fun mapToLocal(updateRemote: UpdateRemote) = UpdateLocal(
        id = updateRemote.id,
        profileImage = updateRemote.profileImage,
        comment = updateRemote.comment,
        createdOn = updateRemote.createdOn?.let { dateParser.parseDateDayMonth(it) },
        createdBy = updateRemote.createdBy,
        infoUrl = updateRemote.infoUrl
    )
}
