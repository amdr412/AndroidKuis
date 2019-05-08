<?php 
	require_once('connection.php');
	
	$sql = "SELECT * FROM sekolah";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	while($row = mysqli_fetch_array($r)){
		
		array_push($result,array(
			"id"=>$row['id'],
			"kode"=>$row['kode'],
			"nama"=>$row['nama'],
			"pemilik"=>$row['pemilik'],
			"alamat"=>$row['alamat']
		));
	}
	
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);