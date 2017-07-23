import { Pv001Page } from './app.po';

describe('pv001 App', () => {
  let page: Pv001Page;

  beforeEach(() => {
    page = new Pv001Page();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
