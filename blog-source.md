
- Bu listedeki kaynakların hepsi daha önce gerçek bir Chrome tarayıcısında test edildi ve geçti. **Ancak senin Playwright ile kullandığın tarayıcı farklı davranabilir.** Bot korumaları (bot protection) otomatik tarayıcılara daha sert davranır. Bu yüzden "önceden geçti" bilgisine güvenme, her kaynağı kendin test et.
- Kaynağın RSS adresi verilmişse önce onu kullan. RSS, ağır sayfalarda ve bot korumalı sitelerde daha güvenilirdir. Liste sayfası açılmıyor ama RSS çalışıyorsa, kaynağı yazı keşfi için kullanabilirsin. Yine de yazı sayfasının tarayıcıda tam metin açılması şart. Açılmıyorsa kaynağı yasakla.
- Çerez (cookie) banner'larını kabul etme. Ya reddet ya da görmezden gel.
- Hiçbir sitede login olma, form doldurma, bültene (newsletter) kaydolma.
- Promptun sonundaki "Önceden yasaklanmış kaynaklar" bölümündeki siteleri hiç kullanma, tekrar test etme.

---

## Kaynak crawl ipuçları

- **VS Code Updates:** URL'yi sonundaki `/` ile aç (`/updates/`). Sayfa en güncel `/updates/vN` sürüm notuna yönlenir.
- **DeepSeek:** Sürüm notları `api-docs.deepseek.com/news/newsYYMMDD` kalıbındadır. `/updates/` sayfasından ve kenar çubuğundan (sidebar) en yenisini bul.
- **Meta:** Güncel Meta AI duyuruları `about.fb.com/news/` üzerinde. `ai.meta.com/blog/` açık ama seyrek güncelleniyor.
- **Microsoft:** `microsoft.ai/blog/` MAI modelleri içindir. Copilot ürün haberleri `blogs.microsoft.com` ve `news.microsoft.com/source/` üzerindedir. Microsoft blogları ve DevBlogs'ta yapay zeka dışı yazılar da var, bunları filtrele.
- **Claude:** `claude.com/blog` adresi `claude.com/resources/articles` adresine yönlenir. Claude Code ile ilgili yazıların bir kısmı `claude.dev` üzerindedir.
- **Fox News, ABC News, AP News:** Liste sayfaları çok ağır. Uzun timeout kullan ve RSS'i tercih et. Bu kaynaklarda işten çıkarma haberleri de çıkıyor, onları ele.
- **China Daily:** Uzun yazılar birden fazla sayfaya bölünüyor ("Next" linkini takip et). Listede eski sabitlenmiş (pinned) yazılar var, tarihe göre sırala.
- **AIBase:** Tarihler göreli yazılıyor ("5 days ago"). Gerçek tarihi yazı sayfasından doğrula.
- **The Verge:** Sitede abonelik programı var. Kilitli bir yazıya denk gelirsen o yazıyı atla.
- **GitHub Releases sayfaları** (Claude Code, Codex, Gemini CLI): Bunlar çok sık güncellenir. Sadece önemli yeni özellikleri al, küçük hata düzeltmelerini (bug fix) alma.
- **Genel haber siteleri ve RSS'ler:** Çoğu RSS sitenin tamamını kapsar, sadece yapay zekayı değil. Başlık ve URL'ye göre yapay zeka içeriklerini filtrele.

---

## Kaynak listesi

İşaretlerin anlamı: **[RSS]** feed adresi. **(seyrek)** son 14 günde yeni içerik yoktu, ama resmi kaynak olduğu için listede. **Not:** crawler için ipucu.

### 1. Resmi şirket kaynakları (newsroom, blog, changelog, release notes) (55)

- **Anthropic Newsroom** - https://www.anthropic.com/news
- **Claude Blog** - https://claude.com/resources/articles - Not: claude.com/blog buraya yönlenir
- **Claude Release Notes** - https://platform.claude.com/docs/en/release-notes/overview - [RSS] https://platform.claude.com/docs/en/release-notes/feed.xml
- **OpenAI News** - https://openai.com/news/ - [RSS] https://openai.com/news/rss.xml
- **ChatGPT Release Notes** - https://help.openai.com/en/articles/6825453-chatgpt-release-notes
- **Official Microsoft Blog** - https://blogs.microsoft.com/ - [RSS] https://blogs.microsoft.com/feed/ | Not: AI dışı kurumsal haberler de var, filtrele
- **Microsoft Source** - https://news.microsoft.com/source/ - [RSS] https://news.microsoft.com/source/feed/ | Not: liste kart tabanlı, RSS tercih et
- **Microsoft AI (MAI modelleri) Blog** - https://microsoft.ai/blog/ - Not: MAI modelleri; Copilot ürün haberleri blogs.microsoft.com ve Microsoft Source üzerinde
- **Microsoft DevBlogs** - https://devblogs.microsoft.com/ - Not: AI dışı (.NET, C++) yazılar da var
- **VS Code Updates** - https://code.visualstudio.com/updates/ - [RSS] https://code.visualstudio.com/feed.xml | Not: sonundaki / gerekli, en güncel /updates/vN sayfasına yönlenir
- **GitHub Blog** - https://github.blog/ - [RSS] https://github.blog/feed/
- **GitHub Changelog** - https://github.blog/changelog/ - [RSS] https://github.blog/feed/
- **Google AI Blog (The Keyword)** - https://blog.google/innovation-and-ai/technology/ai/ - [RSS] https://blog.google/feed
- **Google DeepMind Blog** - https://deepmind.google/blog/
- **Google Developers Blog** - https://developers.googleblog.com/ - [RSS] https://developers.googleblog.com/feeds/posts/default
- **Apple Newsroom** - https://www.apple.com/newsroom/ - [RSS] https://www.apple.com/newsroom/rss-feed.rss
- **Apple Machine Learning Research** - https://machinelearning.apple.com/ - [RSS] https://machinelearning.apple.com/rss.xml
- **NVIDIA Newsroom** - https://nvidianews.nvidia.com/ - [RSS] https://nvidianews.nvidia.com/rss.xml
- **NVIDIA Blog** - https://blogs.nvidia.com/ - [RSS] https://blogs.nvidia.com/feed/
- **xAI News** - https://x.ai/news
- **Cursor Blog** - https://cursor.com/blog - (seyrek, son içerik 2026-09-23)
- **Cursor Changelog** - https://cursor.com/changelog
- **Meta Newsroom** - https://about.fb.com/news/ - [RSS] https://about.fb.com/feed/ | Not: Meta AI duyurularının güncel adresi; AI dışı Meta haberleri de var
- **Mistral AI News** - https://mistral.ai/news/
- **Qwen Blog** - https://qwen.ai/research - (seyrek, son içerik 2026-09-20)
- **Alibaba Cloud Blog** - https://www.alibabacloud.com/blog
- **Z.ai (Zhipu GLM) Release Notes** - https://docs.z.ai/release-notes/new-released - (seyrek, son içerik 2026-08-26)
- **Kimi (Moonshot AI) Blog** - https://www.kimi.ai/blog/ - (seyrek, son içerik 2026-07-16)
- **Hugging Face Blog** - https://huggingface.co/blog - [RSS] https://huggingface.co/blog/feed.xml
- **AWS Machine Learning Blog** - https://aws.amazon.com/blogs/machine-learning/ - [RSS] https://aws.amazon.com/blogs/machine-learning/feed/
- **About Amazon Innovation** - https://www.aboutamazon.com/news/innovation-at-amazon
- **NVIDIA Technical Blog** - https://developer.nvidia.com/blog/
- **Google Research Blog** - https://research.google/blog/
- **Gemini API Changelog** - https://ai.google.dev/gemini-api/docs/changelog
- **Gemini App Release Notes** - https://gemini.google/release-notes/
- **Meta Engineering Blog** - https://engineering.fb.com/ - [RSS] https://engineering.fb.com/feed/
- **AWS News Blog** - https://aws.amazon.com/blogs/aws/ - [RSS] https://aws.amazon.com/blogs/aws/feed/
- **Microsoft Research Blog** - https://www.microsoft.com/en-us/research/blog/ - [RSS] https://www.microsoft.com/en-us/research/feed/
- **Microsoft Azure Blog** - https://azure.microsoft.com/en-us/blog/ - [RSS] https://azure.microsoft.com/en-us/blog/feed/
- **Microsoft Foundry / AI Platform Blog** - https://techcommunity.microsoft.com/category/azure-ai-foundry/blog/azure-ai-foundry-blog
- **Microsoft On the Issues** - https://blogs.microsoft.com/on-the-issues/ - [RSS] https://blogs.microsoft.com/on-the-issues/feed/
- **OpenAI Developer Changelog** - https://developers.openai.com/api/docs/changelog - [RSS] https://developers.openai.com/rss.xml
- **Claude Code GitHub Releases** - https://github.com/anthropics/claude-code/releases - [RSS] https://github.com/anthropics/claude-code/releases.atom
- **Claude Apps Release Notes** - https://support.claude.com/en/articles/12138966-release-notes - (seyrek, son içerik 2026-10-07)
- **Anthropic Research** - https://www.anthropic.com/research
- **OpenAI Codex GitHub Releases** - https://github.com/openai/codex/releases - [RSS] https://github.com/openai/codex/releases.atom
- **Gemini CLI GitHub Releases** - https://github.com/google-gemini/gemini-cli/releases - [RSS] https://github.com/google-gemini/gemini-cli/releases.atom
- **DeepSeek API Updates** - https://api-docs.deepseek.com/updates/ - Not: sürüm notları /news/newsYYMMDD kalıbında
- **Mistral Changelog** - https://docs.mistral.ai/resources/changelogs
- **xAI API Release Notes** - https://docs.x.ai/developers/release-notes
- **Qwen Hugging Face Org** - https://huggingface.co/Qwen
- **Perplexity API Changelog** - https://docs.perplexity.ai/docs/resources/changelog
- **Cohere Blog** - https://cohere.com/blog
- **claude.dev Blog (Claude Code / gelistirici yazilari)** - https://claude.dev/
- **Meta AI Blog** - https://ai.meta.com/blog/ - (seyrek, son içerik 2026-07-27) | Not: açık ama güncel duyurular artık about.fb.com/news üzerinde

### 2. Teknoloji ve yapay zeka haber siteleri (34)

- **TechCrunch AI** - https://techcrunch.com/category/artificial-intelligence/ - [RSS] https://techcrunch.com/feed/
- **The Verge AI** - https://www.theverge.com/ai-artificial-intelligence - [RSS] https://www.theverge.com/rss/index.xml | Not: test edilen yazı tam açıktı, ama sitede abonelik programı var; nadiren kilitli yazı çıkabilir
- **VentureBeat AI** - https://venturebeat.com/category/ai
- **Engadget AI** - https://www.engadget.com/category/ai/ - [RSS] https://www.engadget.com/category/ai/feed/
- **ZDNET AI** - https://www.zdnet.com/topic/artificial-intelligence/ - [RSS] https://www.zdnet.com/rss/all/
- **The Register AI/ML** - https://www.theregister.com/ai_ml/
- **SiliconANGLE AI** - https://siliconangle.com/category/ai/ - [RSS] https://siliconangle.com/category/ai/feed/
- **CNBC AI** - https://www.cnbc.com/ai-artificial-intelligence/
- **AP News AI** - https://apnews.com/hub/artificial-intelligence - Not: ağır sayfa, uzun timeout; layoff haberlerini filtrele
- **9to5Mac** - https://9to5mac.com/ - [RSS] https://9to5mac.com/feed/
- **9to5Google** - https://9to5google.com/ - [RSS] https://9to5google.com/feed/
- **MacRumors** - https://www.macrumors.com/ - [RSS] https://feeds.macrumors.com/MacRumors-All
- **Windows Central AI** - https://www.windowscentral.com/artificial-intelligence - [RSS] https://www.windowscentral.com/feeds.xml
- **The Decoder** - https://the-decoder.com/ - [RSS] https://the-decoder.com/feed/
- **MarkTechPost** - https://www.marktechpost.com/ - [RSS] https://www.marktechpost.com/feed/
- **AI News** - https://www.artificialintelligence-news.com/
- **TestingCatalog** - https://www.testingcatalog.com/ - [RSS] https://www.testingcatalog.com/rss/
- **Pandaily (China tech)** - https://pandaily.com/
- **TechNode (China tech)** - https://technode.com/ - [RSS] https://technode.com/feed/
- **InfoWorld AI** - https://www.infoworld.com/artificial-intelligence/ - [RSS] https://www.infoworld.com/feed/
- **Computerworld AI** - https://www.computerworld.com/artificial-intelligence/ - [RSS] https://www.computerworld.com/feed/
- **The New Stack AI** - https://thenewstack.io/ai/ - [RSS] https://thenewstack.io/ai/feed/
- **InfoQ AI, ML and Data Engineering** - https://www.infoq.com/ai-ml-data-eng/ - [RSS] https://feed.infoq.com/ai-ml-data-eng/
- **Tom's Guide AI** - https://www.tomsguide.com/ai - [RSS] https://www.tomsguide.com/feeds.xml
- **TechRadar AI Platforms and Assistants** - https://www.techradar.com/ai-platforms-assistants - [RSS] https://www.techradar.com/feeds.xml
- **CNET AI Atlas** - https://www.cnet.com/ai-atlas/ - [RSS] https://www.cnet.com/rss/all/
- **Windows Latest** - https://www.windowslatest.com/ - [RSS] https://www.windowslatest.com/feed/
- **The Next Web AI** - https://thenextweb.com/artificial-intelligence
- **ITPro AI** - https://www.itpro.com/technology/artificial-intelligence - [RSS] https://www.itpro.com/feeds.xml
- **AIBase News** - https://www.aibase.com/news - Not: tarihler göreli (5 days ago); layoff içerikli yazıları filtrele
- **China Daily Tech** - https://www.chinadaily.com.cn/business/tech - Not: uzun yazılar sayfalara bölünüyor (Next), tarihe göre sırala
- **Unite.AI** - https://www.unite.ai/ - [RSS] https://www.unite.ai/feed/
- **ABC News AI** - https://abcnews.com/alerts/artificial-intelligence - [RSS] https://abcnews.go.com/abcnews/technologyheadlines | Not: ağır sayfa, RSS tercih et; layoff haberlerini filtrele
- **Semafor Tech** - https://www.semafor.com/vertical/tech - Not: test edilen yazı tam açıktı, çerez banner var

### 3. Hukuk / davalar ve yapay zeka politikası (28)

- **Tech Policy Press** - https://www.techpolicy.press/
- **Lawfare** - https://www.lawfaremedia.org/
- **ChatGPT Is Eating the World (AI litigation)** - https://chatgptiseatingtheworld.com/ - [RSS] https://chatgptiseatingtheworld.com/feed/
- **EFF Deeplinks** - https://www.eff.org/deeplinks
- **White House News** - https://www.whitehouse.gov/news/
- **FTC Press Releases** - https://www.ftc.gov/news-events/news/press-releases
- **The Guardian AI** - https://www.theguardian.com/technology/artificialintelligenceai - [RSS] https://www.theguardian.com/technology/artificialintelligenceai/rss
- **Nextgov/FCW AI** - https://www.nextgov.com/artificial-intelligence/
- **FedScoop AI** - https://fedscoop.com/category/emerging-tech/ - [RSS] https://fedscoop.com/feed/
- **Fox News AI** - https://www.foxnews.com/category/tech/artificial-intelligence - [RSS] https://moxie.foxnews.com/google-publisher/tech.xml | Not: liste sayfası çok ağır, RSS tercih et (tüm Tech, URL içinde artificial-intelligence filtrele)
- **JD Supra AI** - https://www.jdsupra.com/topics/artificial-intelligence/ - Not: AI dışı hukuk yazıları da var, başlığa göre filtrele
- **UK AI Security Institute Blog** - https://www.aisi.gov.uk/blog
- **NIST CAISI (Center for AI Standards and Innovation)** - https://www.nist.gov/caissi
- **European Commission Digital Strategy News (AI Office/AI Act)** - https://digital-strategy.ec.europa.eu/en/news
- **Transformer (AI policy weekly)** - https://www.transformernews.ai/archive - [RSS] https://www.transformernews.ai/feed
- **Just Security - Artificial Intelligence tag** - https://www.justsecurity.org/tag/artificial-intelligence/ - [RSS] https://www.justsecurity.org/feed/
- **Brookings - Artificial Intelligence** - https://www.brookings.edu/topics/artificial-intelligence/ - [RSS] https://www.brookings.edu/feed/
- **Mishcon de Reya Generative AI IP Cases and Policy Tracker** - https://www.mishcon.com/generative-ai-intellectual-property-cases-and-policy-tracker - (seyrek, son içerik 2026-09-18)
- **McKool Smith AI Infringement Case Updates** - https://www.mckoolsmith.com/newsroom-ailitigation - [RSS] https://www.mckoolsmith.com/newsroom-ailitigation?rss | (seyrek, son içerik 2026-09-08)
- **Copyright Lately** - https://copyrightlately.com/ - [RSS] https://copyrightlately.com/feed/
- **IPWatchdog** - https://ipwatchdog.com/
- **Kluwer Copyright Blog** - https://legalblogs.wolterskluwer.com/copyright-blog/
- **Authors Guild News** - https://authorsguild.org/news/
- **Hunton Privacy and Information Security Law Blog** - https://www.hunton.com/privacy-and-cybersecurity-law-blog/ - [RSS] https://www.hunton.com/privacy-and-cybersecurity-law-blog/?rss
- **PBS NewsHour - Artificial Intelligence** - https://www.pbs.org/newshour/tag/artificial-intelligence
- **GovTech - Artificial Intelligence** - https://www.govtech.com/artificial-intelligence - [RSS] https://www.govtech.com/artificial-intelligence.rss
- **Don't Worry About the Vase (Zvi Mowshowitz)** - https://thezvi.substack.com/archive - [RSS] https://thezvi.substack.com/feed
- **AI Safety Newsletter (CAIS)** - https://newsletter.safe.ai/archive - [RSS] https://newsletter.safe.ai/feed

### 4. Bültenler (newsletter), toplayıcılar (aggregator) ve bağımsız analistler (14)

- **Simon Willison** - https://simonwillison.net/ - [RSS] https://simonwillison.net/atom/everything/
- **Techmeme** - https://www.techmeme.com/ - [RSS] https://www.techmeme.com/feed.xml
- **Hacker News** - https://news.ycombinator.com/ - [RSS] https://news.ycombinator.com/rss
- **The Neuron Daily Archive** - https://www.theneurondaily.com/archive - [RSS] https://rss.beehiiv.com/feeds/N4eCstxvgX.xml
- **Import AI Archive** - https://importai.substack.com/archive - [RSS] https://importai.substack.com/feed
- **smol.ai AI News** - https://news.smol.ai/issues - (seyrek, son içerik 2026-09-10)
- **Latent Space Archive** - https://www.latent.space/archive - [RSS] https://www.latent.space/feed
- **The Batch (DeepLearning.AI)** - https://www.deeplearning.ai/the-batch/
- **The Neuron** - https://www.theneuron.ai/ - [RSS] https://www.theneuron.ai/feed/
- **Last Week in AI** - https://lastweekin.ai/archive - [RSS] https://lastweekin.ai/feed
- **Interconnects Archive** - https://www.interconnects.ai/archive - [RSS] https://www.interconnects.ai/feed
- **Artificial Analysis Articles** - https://artificialanalysis.ai/articles
- **Arena (LMArena) Blog** - https://arena.ai/blog
- **LLM Gateway Release Timeline** - https://llmgateway.io/timeline

### Önceden yasaklanmış kaynaklar (kullanma, tekrar test etme)

- Ars Technica AI - https://arstechnica.com/ai/ - human verification / captcha ("Let's confirm you are human" sayfası)
- Reuters AI - https://www.reuters.com/technology/artificial-intelligence/ - human verification / captcha (makalelerde DataDome captcha)
- Tom's Hardware AI - https://www.tomshardware.com/tech-industry/artificial-intelligence - üyelik duvarı (yazının devamı abonelik istiyor)
- Vox AI - https://www.vox.com/artificial-intelligence - üyelik duvarı (yazının devamı abonelik istiyor)
- AI Hallucination Cases Database (Charlotin) - https://www.damiencharlotin.com/hallucinations/ - konu dışı (avukatların sahte AI atıflarına ceza tablosu)
- 36Kr Global (English) - https://eu.36kr.com/en - yazı gövdesi yarıda kesiliyor, tam metin okunamadı
