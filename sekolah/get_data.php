<?php 
	$id = $_GET['id'];
	
	require_once('connection.php');
	
	$sql = "SELECT * FROM sekolah WHERE id=$id";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id"=>$row['id'],
			"kode"=>$row['kode'],
			"nama"=>$row['nama'],
			"pemilik"=>$row['pemilik'],
			"alamat"=>$row['alamat']
		));

	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);