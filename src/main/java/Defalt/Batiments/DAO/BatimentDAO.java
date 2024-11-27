package Defalt.Batiments.DAO;

import Defalt.Batiments.BatimentsMetiers.Batiment;

import java.io.*;
import java.util.List;

/**
 * Classe de gestion de la sérialisation et désérialisation des objets de type
 * {@link Batiment}.
 */
public class BatimentDAO {

	/**
	 * Sérialise une liste d'objets {@link Batiment} dans un fichier.
	 *
	 * @param listBatiments la liste des objets {@link Batiment} à sérialiser.
	 * @param fileName      le nom du fichier dans lequel les objets seront
	 *                      sérialisés.
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     sérialisation.
	 */
	public void serializeBatiments(List<Batiment> listBatiments, String fileName) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
		oos.writeObject(listBatiments);
	}

	/**
	 * Désérialise une liste d'objets {@link Batiment} à partir d'un fichier.
	 *
	 * @param fileName le nom du fichier contenant les objets sérialisés.
	 * @return une liste d'objets {@link Batiment} désérialisés.
	 * @throws IOException            si une erreur d'entrée/sortie survient lors de
	 *                                la désérialisation.
	 * @throws ClassNotFoundException si la classe des objets sérialisés n'est pas
	 *                                trouvée.
	 */
	public List<Batiment> deserializeBatiments(String fileName) throws IOException, ClassNotFoundException {
		List<Batiment> listBatiments = null;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
		listBatiments = (List<Batiment>) ois.readObject();
		return listBatiments;
	}
}
