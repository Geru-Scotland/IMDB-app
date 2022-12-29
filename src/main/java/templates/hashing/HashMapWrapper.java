/*
 * This file is part of the IMDB-app project.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @author - Geru-Scotland (Basajaun)
 * Github: https://github.com/geru-scotland
 */

package templates.hashing;

import entities.models.DataCollection;
import exceptions.EntityNotFoundException;

import java.util.HashMap;

public class HashMapWrapper<T> implements DataCollection<T> {

    HashMap<String, T> hashMap;

    public HashMapWrapper(){
        hashMap = new HashMap<>();
    }

    @Override
    public void add(T data) {
        hashMap.put(data.toString(), data);
    }

    @Override
    public T search(String str) throws EntityNotFoundException {
        if(hashMap.isEmpty())
            throw new EntityNotFoundException("[EXCEPTION] Emtpy HashMap");
        return hashMap.get(str);
    }

    @Override
    public T remove(String str) throws EntityNotFoundException {
        if(hashMap.isEmpty())
            throw new EntityNotFoundException("[EXCEPTION] Emtpy HashMap");
        return hashMap.remove(str);
    }

    @Override
    public boolean remove(T data) throws EntityNotFoundException {
        if(hashMap.isEmpty())
            throw new EntityNotFoundException("[EXCEPTION] Emtpy HashMap");
        return hashMap.remove(data.toString(), data);
    }

    @Override
    public int size() { return hashMap.size(); }
}
