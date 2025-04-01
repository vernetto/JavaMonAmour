(function () {
  const video = document.querySelector('video');
  if (!video) return;

  let speed = 1.0;

  // Create container
  const container = document.createElement('div');
  container.style.position = 'fixed';
  container.style.top = '10px';
  container.style.right = '10px';
  container.style.zIndex = '10000';
  container.style.background = 'rgba(0,0,0,0.7)';
  container.style.color = 'white';
  container.style.padding = '10px';
  container.style.borderRadius = '8px';
  container.style.display = 'flex';
  container.style.alignItems = 'center';
  container.style.gap = '10px';

  // Create down button
  const down = document.createElement('button');
  down.textContent = '−';
  down.onclick = () => {
    speed = Math.max(0.1, speed - 0.1);
    updateSpeed();
  };

  // Create up button
  const up = document.createElement('button');
  up.textContent = '+';
  up.onclick = () => {
    speed = Math.min(16, speed + 0.1);
    updateSpeed();
  };

  // Create input field
  const input = document.createElement('input');
  input.type = 'number';
  input.min = '0.1';
  input.max = '16.0';
  input.step = '0.1';
  input.value = speed.toFixed(1);
  input.style.width = '60px';
  input.onchange = () => {
    const val = parseFloat(input.value);
    if (!isNaN(val) && val >= 0.1 && val <= 16.0) {
      speed = val;
      updateSpeed();
    }
  };

  // Create display span
  const speedDisplay = document.createElement('span');
  speedDisplay.textContent = speed.toFixed(1) + 'x';

  function updateSpeed() {
    video.playbackRate = speed;
    input.value = speed.toFixed(1);
    speedDisplay.textContent = speed.toFixed(1) + 'x';
  }

  container.appendChild(down);
  container.appendChild(input);
  container.appendChild(up);
  container.appendChild(speedDisplay);
  document.body.appendChild(container);

  updateSpeed();
})();
