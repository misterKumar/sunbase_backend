/* static/js/scripts.js */

$(document).ready(function(){


    // Example: Handling sync button click event
    $('.btn-sync').on('click', function() {
        // Make an AJAX request to fetch the customer list
        $.ajax({
            type: 'GET',
            url: '/api/get-customer-list', // Replace with your actual endpoint
            success: function(data) {
                // Assuming the response contains an array of customer objects
                displayCustomerList(data);
            },
            error: function(error) {
                console.error('Error fetching customer list:', error);
            }
        });
    });

    // Example: Handling logout button click event
    $('.btn-logout').on('click', function() {
        // Redirect to the logout endpoint or perform any other logout logic
        window.location.href = '/logout'; // Replace with your actual logout endpoint
    });

    // Function to display the customer list in a table
    function displayCustomerList(customers) {
        // Assuming 'customers' is an array of customer objects
        var tableHtml = '<table><tr><th>First Name</th><th>Last Name</th><th>Email</th><th>Phone</th></tr>';
        for (var i = 0; i < customers.length; i++) {
            tableHtml += '<tr>';
            tableHtml += '<td>' + customers[i].first_name + '</td>';
            tableHtml += '<td>' + customers[i].last_name + '</td>';
            tableHtml += '<td>' + customers[i].email + '</td>';
            tableHtml += '<td>' + customers[i].phone + '</td>';
            tableHtml += '</tr>';
        }
        tableHtml += '</table>';

        // Display the table in the 'table-container' div
        $('.table-container').html(tableHtml);
    }

    // Add more JavaScript code as needed
});
