<?php
$servername = "us-cdbr-iron-east-04.cleardb.net";
$username = "b0179319c7b2ef";
$password = "d319ebe7";
$dbname = "ad_85f9c5363d162e8";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    
}
else{
    
    $chunk = $_POST['myChunk'];
    
    $sql = "SELECT id FROM frase WHERE CONTAINS  (contenuto, ciao) ";
    
    $result = $conn->query($sql);
    
    // se ci sono risultati: li mette in array
    if($result->num_rows >0)
    {
        echo "<br>qua è arrivato<br>";
        $myArray = array();//create an array
        while($row = $result->fetch_assoc()) {
            $myArray[] = $row;
        }
        
        echo "<br>Risultato Query: <br>";
        // e quindi in json
        echo json_encode($myArray);
    }
    //free result
    $result->close();
    //close connection
    $conn->close();

}
?>