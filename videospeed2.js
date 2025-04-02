(function () {
  const video = document.querySelector('video');
  if (!video) return;

  let speed = parseFloat(localStorage.getItem('videoSpeed')) || 1.0;

  const container = document.createElement('div');
  container.style.position = 'fixed';
  container.style.top = localStorage.getItem('panelTop') || '10px';
  container.style.left = localStorage.getItem('panelLeft') || '';
  container.style.right = localStorage.getItem('panelRight') || '10px';
  container.style.zIndex = '10000';
  container.style.background = 'rgba(0,0,0,0.8)';
  container.style.color = 'white';
  container.style.padding = '10px';
  container.style.borderRadius = '8px';
  container.style.display = 'flex';
  container.style.flexDirection = 'column';
  container.style.fontFamily = 'Arial';
  container.style.userSelect = 'none';
  container.style.cursor = 'default';

  // Title bar
  const titleBar = document.createElement('div');
  titleBar.style.display = 'flex';
  titleBar.style.justifyContent = 'space-between';
  titleBar.style.alignItems = 'center';
  titleBar.style.marginBottom = '8px';
  titleBar.style.cursor = 'move';

  const title = document.createElement('span');
  title.textContent = 'Speed Control';

  const closeBtn = document.createElement('button');
  closeBtn.textContent = '×';
  Object.assign(closeBtn.style, {
    marginLeft: '10px',
    background: 'transparent',
    color: 'white',
    border: 'none',
    fontSize: '16px',
    cursor: 'pointer'
  });
  closeBtn.onclick = () => container.remove();

  titleBar.appendChild(title);
  titleBar.appendChild(closeBtn);
  container.appendChild(titleBar);

  // Controls row
  const controls = document.createElement('div');
  controls.style.display = 'flex';
  controls.style.alignItems = 'center';
  controls.style.gap = '10px';
  controls.style.marginBottom = '8px';

  const buttonStyle = {
    background: 'none',
    border: '1px solid white',
    color: 'white',
    padding: '4px 8px',
    borderRadius: '4px',
    cursor: 'pointer'
  };

  const down = document.createElement('button');
  down.textContent = '−';
  Object.assign(down.style, buttonStyle);
  down.onclick = () => {
    speed = Math.max(0.1, speed - 0.1);
    updateSpeed();
  };

  const up = document.createElement('button');
  up.textContent = '+';
  Object.assign(up.style, buttonStyle);
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

  // Presets row
  const presets = document.createElement('div');
  presets.style.display = 'flex';
  presets.style.flexWrap = 'wrap';
  presets.style.gap = '10px';

  const createPresetBtn = (label, value) => {
    const btn = document.createElement('button');
    btn.textContent = label;
    Object.assign(btn.style, buttonStyle);
    btn.onclick = () => {
      speed = value;
      updateSpeed();
    };
    return btn;
  };

  [1.0, 1.5, 2.0, 2.5, 3.0].forEach(s =>
    presets.appendChild(createPresetBtn(`${s}x`, s))
  );

  container.appendChild(presets);
  document.body.appendChild(container);

  function updateSpeed() {
    video.playbackRate = speed;
    input.value = speed.toFixed(1);
    speedDisplay.textContent = speed.toFixed(1) + 'x';
    localStorage.setItem('videoSpeed', speed.toFixed(1));
  }

  if (video.readyState >= 1) {
    updateSpeed();
  } else {
    video.addEventListener('loadedmetadata', updateSpeed);
  }

  // Draggable support for mouse
  let isDragging = false;
  let offsetX = 0, offsetY = 0;

  const startDrag = (x, y) => {
    isDragging = true;
    offsetX = x - container.offsetLeft;
    offsetY = y - container.offsetTop;
    document.body.style.userSelect = 'none';
  };

  const moveDrag = (x, y) => {
    if (isDragging) {
      container.style.left = x - offsetX + 'px';
      container.style.top = y - offsetY + 'px';
      container.style.right = 'auto';
      localStorage.setItem('panelLeft', container.style.left);
      localStorage.setItem('panelTop', container.style.top);
      localStorage.setItem('panelRight', 'auto');
    }
  };

  titleBar.addEventListener('mousedown', e => startDrag(e.clientX, e.clientY));
  document.addEventListener('mousemove', e => moveDrag(e.clientX, e.clientY));
  document.addEventListener('mouseup', () => {
    isDragging = false;
    document.body.style.userSelect = '';
  });

  // Touch support
  titleBar.addEventListener('touchstart', e => {
    if (e.touches.length === 1) {
      const touch = e.touches[0];
      startDrag(touch.clientX, touch.clientY);
    }
  }, { passive: true });

  document.addEventListener('touchmove', e => {
    if (e.touches.length === 1 && isDragging) {
      const touch = e.touches[0];
      moveDrag(touch.clientX, touch.clientY);
    }
  }, { passive: true });

  document.addEventListener('touchend', () => {
    isDragging = false;
    document.body.style.userSelect = '';
  });
})();
