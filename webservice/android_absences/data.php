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

			$SQL = "select id, dtDebut, duree, idEnseignant, idGroupe, nom from seances WHERE idEnseignant=$_SESSION[id] $clause";
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
				$SQL = "select u.id, u.nom, u.prenom from users u, seances s, membres m   where "; 
				$SQL .= "s.id = $_GET[idSeance] "; 
				$SQL .= "AND s.idGroupe = m.idGroupe ";
				$SQL .= "AND u.id = m.idEleve ";
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
					$SQL = "insert into data(idEleve,idSeance,boolAbsent) VALUES('$idEleve','$idSeance','1')"; 
					SQLInsert($SQL); 
				}
				
			}
		break;
		
		// setPresences(JSON_presences) : à compléter


		default: 
			$data["feedback"]= "action inconnue"; 
		break;

	}
}



	echo json_encode($data);
	die("");
?>