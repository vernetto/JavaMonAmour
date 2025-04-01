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
  container.style.background = 'rgba(0,0,0,0.8)';
  container.style.color = 'white';
  container.style.padding = '10px';
  container.style.borderRadius = '8px';
  container.style.display = 'flex';
  container.style.flexDirection = 'column';
  container.style.fontFamily = 'Arial';
  container.style.userSelect = 'none';
  container.style.cursor = 'move';

  // Create title bar with close button
  const titleBar = document.createElement('div');
  titleBar.style.display = 'flex';
  titleBar.style.justifyContent = 'space-between';
  titleBar.style.alignItems = 'center';
  titleBar.style.marginBottom = '8px';

  const title = document.createElement('span');
  title.textContent = 'Speed Control';

  const closeBtn = document.createElement('button');
  closeBtn.textContent = '×';
  closeBtn.style.marginLeft = '10px';
  closeBtn.style.background = 'transparent';
  closeBtn.style.color = 'white';
  closeBtn.style.border = 'none';
  closeBtn.style.fontSize = '16px';
  closeBtn.style.cursor = 'pointer';
  closeBtn.onclick = () => container.remove();

  titleBar.appendChild(title);
  titleBar.appendChild(closeBtn);
  container.appendChild(titleBar);

  // Create controls row
  const controls = document.createElement('div');
  controls.style.display = 'flex';
  controls.style.alignItems = 'center';
  controls.style.gap = '10px';

  const down = document.createElement('button');
  down.textContent = '−';
  down.onclick = () => {
    speed = Math.max(0.1, speed - 0.1);
    updateSpeed();
  };

  const up = document.createElement('button');
  up.textContent = '+';
  up.onclick = () => {
    speed = Math.min(16, speed + 0.1);
    updateSpeed();
  };

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

  const speedDisplay = document.createElement('span');
  speedDisplay.textContent = speed.toFixed(1) + 'x';

  controls.appendChild(down);
  controls.appendChild(input);
  controls.appendChild(up);
  controls.appendChild(speedDisplay);
  container.appendChild(controls);

  document.body.appendChild(container);

  function updateSpeed() {
    video.playbackRate = speed;
    input.value = speed.toFixed(1);
    speedDisplay.textContent = speed.toFixed
