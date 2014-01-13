<?php
session_start(); 
include_once("sql.php");


// peut-être a-t-il fourni des identifiants ?
	



		
if ( isset($_GET["login"]) && isset($_GET["passe"]) )
{
	//echo "recu : " . $_GET["login"] . " et " . $_GET["passe"] ; 
	$sql = "select nom,prenom,id from users where login='$_GET[login]' and passe='$_GET[passe]'";
	if ($tabUsers=SQLSelect($sql))
	{
		$dataUser = mysql_fetch_assoc($tabUsers);
		$_SESSION["id"] = $dataUser["id"];
		$data["connecte"] = "true";
		$data["feedback"] = "entrez action: logout, changerPasse(passe), getListeSeances([date=2013-12-12 08:00]), getListeEleves(idSeance), setAbsents(idSeance,listeIdAbsents), getProfil(idUser)";
		$data["id"] = $dataUser["id"];
		$data["nom"] = $dataUser["nom"];
		$data["prenom"] = $dataUser["prenom"];
		echo json_encode($data);
		die("");
	}
} 

// Semble mal foutu : à revoir... 

if (!isset($_SESSION["id"]))
{
	$data["connecte"] = "false";
	$data["feedback"] = "Entrez login,passe (eg 'user<i>','passe<i>')";	 
	echo json_encode($data);
	die("");
}
else
{
	$data["connecte"] = "true";
	$data["feedback"] = "entrez action: changerPasse(passe),logout, getListeSeances([date=2013-12-12 08:00]), getListeEleves(idSeance),  setAbsents(idSeance,listeIdAbsents), getProfil(idUser)";		 
}


if (isset($_GET["action"])) 
{

	switch($_GET["action"])
	{

		case "logout": 
			unset($_SESSION["id"]);
			$data["connecte"] = "false";
			$data["feedback"] = "deconnexion reussie";
		break; 

		case "changerPasse": 
			if (isset($_GET["passe"])) 
			{
				$passe = addslashes($_GET["passe"]);	
				$SQL = "UPDATE users SET passe='$passe' WHERE id=$_SESSION[id]";
				SQLInsert($SQL);
				$data["feedback"] = "passe enregistre";
			}
			else 
			{
				$data["feedback"] = "passe manquant";
			}
			
		break;


		case "getListeSeances": 
			if (isset($_GET["date"]))
			{
				$clause = "AND dtDebut LIKE '$_GET[date]%'" ;
			} else $clause="";
			// faudrait que la scolarité puisse afficher n'importe quelle séance

			$SQL = "select id, dtDebut, duree, idEnseignant, idGroupe, nom, validee from seances WHERE idEnseignant=$_SESSION[id] $clause";
			$tab = parcoursRs(SQLSelect($SQL));		
			$data["seances"] = $tab;
		break;



		case "getListeEleves": 
			if (!isset($_GET["idSeance"]))
			{
				$data["feedback"] = "getListeEleves: entrez idSeance";	 
			}
			else
			{

				// Faudrait interdire d'afficher des séances dont l'utilisateur n'est pas enseignant
				$SQL = "select u.id, u.nom, u.prenom, d.boolPresence, d.boolRetard, d.signature from seances s
							join membres m on s.idGroupe = m.idGroupe
								join users u on m.idEleve = u.id
									left join data d on u.id = d.idEleve and s.id = d.idSeance
							where s.id = $_GET[idSeance] "; 
				$tab = parcoursRs(SQLSelect($SQL));		
				$data["eleves"] = $tab;
			}
		break;


		case "getProfil": 
			if (!isset($_GET["idUser"]))
			{
				$data["feedback"] = "getProfil: entrez idUser";	 
			}
			else
			{

				$SQL = "select id,nom,prenom from users where "; 
				$SQL .= "id = $_GET[idUser]";
				$tab = parcoursRs(SQLSelect($SQL));		
				$data["profil"] = $tab;
			}
		break;

		case "setAbsents": 
			if (!isset($_GET["idSeance"]))
			{
				$data["feedback"] = "setAbsents: entrez idSeance";	 
			}
			else if (!isset($_GET["listeAbsents"]))
			{
				$data["feedback"] = "setAbsents: entrez listeAbsents";	 
			}
			else
			{
				$idSeance = $_GET["idSeance"]; 
				$listeAbsents = $_GET["listeAbsents"]; 
				// On parcourt la liste
				$lstAbsents = explode(",",$listeAbsents);
				//echo "<pre>";
				//print_r($lstAbsents); 
				//echo "</pre>";
				foreach ($lstAbsents as $idEleve)
				{
					$SQL = "insert into data(idEleve,idSeance,boolPresence) VALUES('$idEleve','$idSeance','0')"; 
					SQLInsert($SQL); 
				}
				
			}
		break;
		
		case "setPresences": 
			if (!isset($_GET["data"]))
			{
				$data["feedback"] = "setPresences: entrez les data";	 
			}
			else {
				$data = json_decode($_GET['data']);
				foreach ($data as $eleve)
				{
					$SQL = "insert into data(idEleve,idSeance,boolPresence, boolRetard, signature) VALUES('$eleve->idEleve','$eleve->idSeance','$eleve->boolPresence', '$eleve->boolRetard', '$eleve->signature')"; 
					SQLInsert($SQL); 
					
					$SQL = "update seances set validee='1' where id ='$eleve->idSeance'"; 
					SQLUpdate($SQL);
					
					//var_dump($eleve);
				}
			}
		break;
		

		default: 
			$data["feedback"]= "action inconnue"; 
		break;

	}
}



	echo json_encode($data);
	die("");
?>
