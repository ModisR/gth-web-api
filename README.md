# Gallifreyan Translation Helper Web API

The goal of this project is to provide a more convenient interface with which the
[Discord bot](https://github.com/Mightyfrong/gth-discord-bot) can interact. Currently, it uses the clunkier approach of
[puppeteer-ing](https://github.com/puppeteer/puppeteer) the
[Gallifreyan Translation Helper web page](https://github.com/Mightyfrong/gallifreyan-translation-helper).

This API is in the proof of concept stage, merely demonstrating the ability to:

1. process apostrophes, hyphens, and the letters A-Z and
2. group them together into appropriate stacks as specified by [Loren Sherman](https://shermansplanet.com/gallifreyan/guide.pdf)

when the endpoint `GET /v1/gth/translate/:text` is called.