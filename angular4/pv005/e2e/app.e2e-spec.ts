import { Pv003Page } from './app.po';

describe('pv003 App', () => {
  let page: Pv003Page;

  beforeEach(() => {
    page = new Pv003Page();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
