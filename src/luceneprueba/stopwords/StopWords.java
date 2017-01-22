/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba.stopwords;

import java.util.ArrayList;

/** Clase hasta el momento no utilizada ya que se utiliza stop words que viene
 * por default en Lucene.
 *
 * @author Gonzalo
 */

// TODO: Leer desde un fichero las stopwords a utilizar
@SuppressWarnings("serial")
public class StopWords extends ArrayList<String> {
    
    public StopWords() {
        this.add("a+");
        this.add("accurate");
	this.add("accurately");
    }
    
}
