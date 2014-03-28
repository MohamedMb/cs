package benchmark;


/**
 * Montre comment utiliser et récupérer les résultats
 * 
 * @author Mohamed
 *
 */
public class BenchmarkLauncher {
	/**
	 * Tout les resultats se passent sur 1000 connexions
	 * Alors attention, exécuter cette requête dans mySql avant de lancer
	 * set global max_connections=10000
	 * 
	 * 
	 * Durant ces tests, des insertions sont faites, pour nettoyer la base, exécuter ces
	 * 3 requêtes l'une à la suite de l'autre (surtout avant de faire des tests sur les selects)
	 * SET SQL_SAFE_UPDATES = 0;
	 * delete from utilisateur where nom = 'test';
	 * SET SQL_SAFE_UPDATES = 1;
	 * @param args
	 * @throws Exception
	 */
	
	public static void main (String[] args) throws Exception {
		//Dé-commenter pour lancer le test voulue
		//Pour changer le nb de connexion utiliser MySQLConnexionTest.NB_CONNEXION = xxxxx
		//et PoolConnexionTest.MAX_CONNEXION = xxxxx
		//Un conseil, ne pas dépasser les 2200 connexions
		
		/*
		MySQLConnexionTest connexionAvecThreadSelect = new MySQLConnexionTest(true, 1, "select");
		connexionAvecThreadSelect.startBenchmark();
		HashMap<String, String> resultConnexionAvecThreadSelect = connexionAvecThreadSelect.getDureesConnexions();
		System.out.println(resultConnexionAvecThreadSelect);
		//Manière de récupérer les résultats
		Iterator<Integer> keyHashMapIteratorTS = resultConnexionAvecThreadSelect.keySet().iterator();
		while (keyHashMapIteratorTS.hasNext()) {
			String key = keyHashMapIteratorTS.next(); //nb connexion
			String value = resultConnexionAvecThreadSelect.get(key); //durée
			System.out.println(key + " --> " + value);
		}
		
		//Select connexion simple sans thread
		MySQLConnexionTest connexionSansThreadSelect = new MySQLConnexionTest(false, 1, "select");
		connexionSansThreadSelect.startBenchmark();
		HashMap<String, String> resultConnexionSansThreadSelect = connexionSansThreadSelect.getDureesConnexions();
		System.out.println(resultConnexionSansThreadSelect);
		*/
		
		
		//Select pool de connexion sans thread
		/*PoolConnexionTest poolConnexionSansThreadSelect = new PoolConnexionTest(false, 1, "select");
		poolConnexionSansThreadSelect.startBenchmark();
		HashMap<String, String> resultPoolConnexionSansThreadSelect = poolConnexionSansThreadSelect.getDureesConnexions();
		System.out.println(resultPoolConnexionSansThreadSelect);*/
		/*
		//Insert connexion simple avec thread
		MySQLConnexionTest connexionAvecThreadInsert = new MySQLConnexionTest(true, 1, "insert");
		connexionAvecThreadInsert.startBenchmark();
		HashMap<String, String> resultConnexionAvecThreadInsert = connexionAvecThreadInsert.getDureesConnexions();
		System.out.println(resultConnexionAvecThreadInsert);		
		
		//Insert connexion simple sans thread
		MySQLConnexionTest connexionSansThreadInsert = new MySQLConnexionTest(false, 1, "insert");
		connexionSansThreadInsert.startBenchmark();
		HashMap<String, String> resultConnexionSansThreadInsert = connexionSansThreadInsert.getDureesConnexions();
		System.out.println(resultConnexionSansThreadInsert);
		
		//Insert pool de connexion avec thread
		PoolConnexionTest poolConnexionAvecThreadInsert = new PoolConnexionTest(true, 1, "insert");
		poolConnexionAvecThreadInsert.startBenchmark();
		HashMap<String, String> resultPoolConnexionAvecThreadInsert = poolConnexionAvecThreadInsert.getDureesConnexions();
		System.out.println(resultPoolConnexionAvecThreadInsert);
		
		//Insert pool de connexion sans thread
		PoolConnexionTest poolConnexionSansThreadInsert = new PoolConnexionTest(false, 1, "insert");
		poolConnexionSansThreadInsert.startBenchmark();
		HashMap<String, String> resultPoolConnexionSansThreadInsert = poolConnexionSansThreadInsert.getDureesConnexions();
		System.out.println(resultPoolConnexionSansThreadInsert);
		*/
		PoolConnexionTest poolConnexionSansThreadSelect = new PoolConnexionTest(false, 1, "select");
		poolConnexionSansThreadSelect.startBenchmark();
		System.out.println(poolConnexionSansThreadSelect);
	}
}
