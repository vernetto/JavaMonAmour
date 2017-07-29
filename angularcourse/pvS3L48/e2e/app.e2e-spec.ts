import { PvS3L41Page } from './app.po';

describe('pv-s3-l41 App', () => {
  let page: PvS3L41Page;

  beforeEach(() => {
    page = new PvS3L41Page();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
