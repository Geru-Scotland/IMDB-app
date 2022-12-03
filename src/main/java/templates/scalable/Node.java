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

import exceptions.EntityNotFoundException;

public class Node<T extends Comparable<T>> {
    private Node<T> left;
    private Node<T> right;
    private final T info;

    public Node(T data){ info = data; }

    /**
     * Basic Node methods.
     */
    private boolean isLeaf(){ return left == null && right == null;}
    private boolean hasRight(){ return right != null; }
    private boolean hasLeft(){ return left != null; }

    public void add(T data){
        if(info.compareTo(data) < 0)
            if(hasRight())
                right.add(data);
            else
                right = new Node<>(data);
        else
            if(hasLeft())
                left.add(data);
            else
                left = new Node<>(data);
    }

    public T search(String str) throws EntityNotFoundException {
        if(isLeaf() && !info.toString().equals(str))
            throw new EntityNotFoundException("Entidad no encontrada");
        return info;
    }

    public T remove(String str){
        return null;
    }

}
