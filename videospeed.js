javascript:(function() {
    // Check if the overlay already exists
    if (document.getElementById('videoSpeedController')) {
        return; // Prevent creating multiple overlays
    }

    // Create the overlay container
    var overlay = document.createElement('div');
    overlay.id = 'videoSpeedController';
    overlay.style.position = 'fixed';
    overlay.style.bottom = '20px';
    overlay.style.right = '20px';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    overlay.style.color = 'white';
    overlay.style.padding = '10px';
    overlay.style.borderRadius = '5px';
    overlay.style.zIndex = '9999';
    overlay.style.fontFamily = 'Arial, sans-serif';

    // Create buttons for increasing and decreasing speed
    var increaseBtn = document.createElement('button');
    increaseBtn.textContent = '+';
    increaseBtn.style.margin = '0 5px';
    increaseBtn.style.padding = '5px 10px';
    increaseBtn.style.fontSize = '16px';
    increaseBtn.style.cursor = 'pointer';
    increaseBtn.onclick = function() {
        var video = document.querySelector('video');
        if (video) {
            video.playbackRate += 0.1;
            updateSpeedDisplay(video.playbackRate);
        }
    };

    var decreaseBtn = document.createElement('button');
    decreaseBtn.textContent = '-';
    decreaseBtn.style.margin = '0 5px';
    decreaseBtn.style.padding = '5px 10px';
    decreaseBtn.style.fontSize = '16px';
    decreaseBtn.style.cursor = 'pointer';
    decreaseBtn.onclick = function() {
        var video = document.querySelector('video');
        if (video) {
            video.playbackRate = Math.max(0.1, video.playbackRate - 0.1);
            updateSpeedDisplay(video.playbackRate);
        }
    };

    // Create a display for the current speed
    var speedDisplay = document.createElement('span');
    speedDisplay.id = 'speedDisplay';
    speedDisplay.textContent = '1.0x';

    function updateSpeedDisplay(speed) {
        speedDisplay.textContent = speed.toFixed(1) + 'x';
    }

    // Add buttons and speed display to the overlay
    overlay.appendChild(decreaseBtn);
    overlay.appendChild(speedDisplay);
    overlay.appendChild(increaseBtn);

    // Add a close button
    var closeBtn = document.createElement('button');
    closeBtn.textContent = '×';
    closeBtn.style.marginLeft = '10px';
    closeBtn.style.padding = '5px';
    closeBtn.style.cursor = 'pointer';
    closeBtn.style.backgroundColor = 'red';
    closeBtn.style.color = 'white';
    closeBtn.style.border = 'none';
    closeBtn.style.borderRadius = '3px';
    closeBtn.onclick = function() {
        document.body.removeChild(overlay);
    };
    overlay.appendChild(closeBtn);

    // Add the overlay to the document body
    document.body.appendChild(overlay);
})();
