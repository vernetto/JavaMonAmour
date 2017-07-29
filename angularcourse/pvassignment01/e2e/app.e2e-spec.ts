import { Pvassignment01Page } from './app.po';

describe('pvassignment01 App', () => {
  let page: Pvassignment01Page;

  beforeEach(() => {
    page = new Pvassignment01Page();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
