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
    private T info;

    public Node(T data){ info = data; }

    /**
     * Basic Node methods.
     */
    private boolean isLeaf(){ return left == null && right == null;}
    private boolean hasRight(){ return right != null; }
    private boolean hasLeft(){ return left != null; }
    public T getInfo(){ return info; }

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
        if(info.toString().equals(str))
            return info;
        
        if(isLeaf())
            throw new EntityNotFoundException("[EXCEPTION] Entidad no encontrada");

        if(info.toString().compareTo(str) < 0)
            if(hasRight())
                return right.search(str);
            else
                throw new EntityNotFoundException("[EXCEPTION] Entidad no encontrada");

        if(hasLeft())
            return left.search(str);

        throw new EntityNotFoundException("[EXCEPTION] Entidad no encontrada");
    }

    public void display(){
        System.out.println(info.toString());
        if(hasRight())
            right.display();
        if(hasLeft())
            left.display();
    }

    /**
     * Elimina un intérprete del árbol (puede seguir estando en las listas de intérpretes de las películas)
     * Implementación de transparencias.
     * @param str Nombre del intérprete a eliminar
     * @return el Interprete (si se ha eliminado), null en caso contrario
     */
    public Aux<T> remove(String str){
        int comp = str.compareTo(info.toString());
        Aux<T> res = new Aux<>();

        if(comp == 0){
            res.info = info;
            if(!hasLeft()){
                res.node = right;
                return res;
            }
            else if(!hasRight()){
                res.node = left;
                return res;
            }
            else{
                Aux<T> min = right.removeMin();
                right = min.node;
                info = min.info;
                res.node = this;
                return res;
            }
        } else if(comp < 0){
            if(hasLeft()){
                res = left.remove(str);
                left = res.node;
            }
            res.node = this;
            return res;
        } else{
            if(hasRight()){
                 res = right.remove(str);
                 right = res.node;
            }
            res.node = this;
            return res;
        }
    }

    /**
     * Función auxiliar que elimina y devuelve el mínimo de del subárbol derecho.
     * @return Objeto de la clase auxiliar Aux.
     */
    public Aux<T> removeMin(){
        Aux<T> res = new Aux<>();
        if(!this.hasLeft()) {
            res.info = this.info;
            res.node = this.right;
        }else {
            Aux<T> resulLeft = this.left.removeMin();
            this.left = resulLeft.node;
            res.info = resulLeft.info;
            res.node = this;
        }
        return res;
    }

    class Aux<T extends Comparable<T>> {
        private T info;
        private Node<T> node;
        public Aux(){}

        public T getInfo(){ return info; }
    }
}
