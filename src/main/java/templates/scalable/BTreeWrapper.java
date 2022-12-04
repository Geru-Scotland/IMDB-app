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

import entities.models.DataCollection;
import exceptions.EntityNotFoundException;

/**
 * Clase genérica encargada de gestionar colecciones de datos NO LINEALES. Hereda de
 * ciertas funcionalidades arraigadas a su estructura de la interfaz BTreeEngine.
 * @param <T> El tipo T debe de heredar de Comparable e implementar la interfaz Entity.
 *
 */
public class BTreeWrapper<T extends Comparable<T>> implements DataCollection<T> {

    private Node<T> root;
    private int numNodes;

    public BTreeWrapper(){
        root = null;
        numNodes = 0;
    }

    public boolean isEmpty() { return root == null; }

    /**
     * DataCollection overrides.
     */
    @Override
    public void add(T node){
        if(!isEmpty())
            root.add(node);
        else
            root = new Node<>(node);

        numNodes++;
    }

    @Override
    public T search(String str) throws EntityNotFoundException {
        if(!isEmpty())
            return root.search(str);
        return null;
    }

    /**
     * Elimina un intérprete del árbol (puede seguir estando en las listas de intérpretes de las películas)
     * @param str Nombre del intérprete a eliminar
     * @return el Interprete (si se ha eliminado), null en caso contrario
     */
    @Override
    public T remove(String str) throws EntityNotFoundException {
        if(!isEmpty()){
            T entity = root.remove(str).getInfo();
            if(entity != null){
                numNodes--;
                return entity;
            }
            else
                throw new EntityNotFoundException("Entidad no encontrada");
        }
        throw new EntityNotFoundException("Arbol vacio.");
    }

    @Override
    public boolean remove(T data){ return false; }

    @Override
    public int size(){ return numNodes; }
}
