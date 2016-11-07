app.filter('swapDayMonth', function()
{
    return function(input)
    {
        try {
            var parts = input.split('/');
            return parts[1] + '/' + parts[0] + '/' + parts[2];
        } catch(e) {
            return input;
        }
    }

});