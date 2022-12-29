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

public class HashWrapper <T extends Comparable<T>> implements DataCollection<T> {
    @Override
    public void add(T node) {

    }

    @Override
    public T search(String str) throws EntityNotFoundException {
        return null;
    }

    @Override
    public T remove(String str) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean remove(T data) throws EntityNotFoundException {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
