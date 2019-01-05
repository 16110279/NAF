<?php 
 require_once 'koneksi.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 	$nama = $_POST['nama'];
 	$harga = $_POST['harga'];
 	$foto = $_POST['foto'];
	$path = "Uploads/$nama.jpg";

 	$query = "INSERT INTO tbl_food (name_food, price_food,image_food) VALUES ('$nama','$harga','$path')";

 	$exeQuery = mysqli_query($konek, $query); 
	file_put_contents($path,base64_decode($foto));
 	echo ($exeQuery) ?  json_encode(array('kode' =>1, 'pesan' => 'berhasil menambahkan data')) :  json_encode(array('kode' =>2, 'pesan' => 'data gagal ditambahkan'));
 }
 else
 {
 	 echo json_encode(array('kode' =>101, 'pesan' => 'request tidak valid'));
 }

 ?>