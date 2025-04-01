(function () {
  const speeds = [0.5, 1, 1.25, 1.5, 2, 2.5];
  let speedDisplay;

  function createSpeedControl() {
    speedDisplay = document.createElement('div');
    speedDisplay.style.position = 'fixed';
    speedDisplay.style.bottom = '10px';
    speedDisplay.style.right = '10px';
    speedDisplay.style.backgroundColor = 'rgba(0,0,0,0.7)';
    speedDisplay.style.color = 'white';
    speedDisplay.style.padding = '10px';
    speedDisplay.style.fontSize = '16px';
    speedDisplay.style.cursor = 'pointer';
    speedDisplay.style.zIndex = 9999;
    speedDisplay.style.borderRadius = '5px';
    speedDisplay.title = 'Click to change playback speed';

    document.body.appendChild(speedDisplay);

    speedDisplay.addEventListener('click', () => {
      const input = prompt('Enter playback speed (e.g., 1.25):');
      if (input !== null) {
        const newSpeed = parseFloat(input);
        if (!isNaN(newSpeed) && newSpeed > 0) {
          setPlaybackRate(newSpeed);
        } else {
          alert('Invalid speed value');
        }
      }
    });
  }

  function setPlaybackRate(rate) {
    const videos = document.querySelectorAll('video');
    videos.forEach(video => {
      video.playbackRate = rate;
    });
    speedDisplay.textContent = `Speed: ${rate}x`;
  }

  function init() {
    createSpeedControl();
    const defaultSpeed = 1;
    setPlaybackRate(defaultSpeed);
  }

  init();
})();
