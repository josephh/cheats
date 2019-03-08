# deploy release to NPM private repo
(you should be logged in to NPM)
git checkout -b release/190305-1 // branch from develop
npm version patch
npm run deliver-release
