<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$pemilik = $_POST['pemilik'];
		$alamat = $_POST['alamat'];
		
		if($kode == '' || $nama == '' || $pemilik == '' || $alamat == ''){
			echo 'Data tidak boleh ada yang kosong!';
		}else{
			require_once('connection.php');
			$sql = "SELECT * FROM sekolah WHERE kode='$kode'";
			
			$check = mysqli_fetch_array(mysqli_query($con,$sql));
			
			if(isset($check)){
				echo 'Nama data sudah ada';
			}else{				
				$sql = "INSERT INTO sekolah (kode,nama,pemilik,alamat) VALUES('$kode','$nama','$pemilik','$alamat')";
				if(mysqli_query($con,$sql)){
					echo 'Data berhasil disimpan';
				}else{
					echo 'oops! Mohon coba lagi!';
				}
			}
			mysqli_close($con);
		}
}else{
echo 'error';
}