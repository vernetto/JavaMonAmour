(function () {
  const video = document.querySelector('video');
  if (!video) return;

  let speed = 1.0;

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
  container.style.cursor = 'default';

  // Title bar (for dragging + close button)
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

  // Controls row
  const controls = document.createElement
