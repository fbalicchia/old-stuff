// The root URL for the RESTful services
var rootURL = "http://localhost:9090/rest";


// Retrieve wine list when application starts
findAll();

$('#btnSearch').click(function() {
  search($('#searchKey').val());
  return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
  if(e.which == 13) {
    search($('#searchKey').val());
    e.preventDefault();
    return false;
  }
});


$('#godList a').live('click', function() {
  findById($(this).data('identity'));
});

function search(searchKey) {
  if (searchKey == '')
    findAll();
  else
    findByName(searchKey);
}


function findAll() {
  $.ajax({
    type: 'GET',
    url: rootURL + '/listVertices',
    dataType: "json", // data type of response
    success: renderList
  });
}

function findByName(searchKey) {
  console.log('findByName: ' + searchKey);
  $.ajax({
    type: 'GET',
    url: rootURL + '/search/' + searchKey,
    dataType: "json",
    success: renderList
  });
}


function renderList(data) {
  var list = data == null ? [] : data.split(',');
  $('#goodList li').remove();
  $.each(list, function(index, god) {
    $('#goodList').append('<li><a href="#" data-identity="' + god + '">'+god+'</a></li>');
  });
}

