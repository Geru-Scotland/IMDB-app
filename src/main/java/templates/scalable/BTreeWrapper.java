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

package templates.scalable;

import templates.scalable.BTreeEngine;

public class BTreeWrapper<T> implements BTreeEngine<T> {

    private Node<T> root;
    private int numNodes;

    public BTreeWrapper(){
        root = null;
        numNodes = 0;
    }

    public boolean isEmpty() { return root == null; }
    /**
     * BTreeEngine overrides.
     */

    @Override
    public void add(T node){
        numNodes++;
    }

    @Override
    public T search(String str){
        if(!isEmpty())
            return root.search(str);
        return null;
    }

    @Override
    public T remove(String str){
        if(!isEmpty())
            return root.remove(str);
        return null;
    }

    @Override
    public int size(){
        return numNodes;
    }
}
