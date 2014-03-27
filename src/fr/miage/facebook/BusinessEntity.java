/**
 * Classe abstraite dont hérite toutes les classes métier de l'application
 */
package fr.miage.facebook;

import java.io.Serializable;

/**
 * @author Stephane
 * @version 0.1
 *
 */
public abstract class BusinessEntity implements Serializable {
	/**
	 * Identifiant de l'objet
	 */
	private Integer id;

	/**
	 * 
	 * @return L'identifiant de l'objet
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * L'identifiant à définir
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Teste l'égalité entre deux objets métiers
	 * @param entity
	 * @return Vrai si entity est équivalent à l'objet en cours
	 */
	public boolean equals(BusinessEntity entity){
		boolean equals = super.equals(entity);
		if (!equals){
			if (this.getClass().equals(entity)){
				equals = this.getId().equals(entity.getId());
			}
		}
		return equals;
	}
	/**
	 * @return Représentation textuel de l'entité
	 */
	public String toString(){
		return this.getClass().getSimpleName() + " n°" + this.getId();
	}
	
	
}
