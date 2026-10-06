import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';

const features = [
  {
    title: 'AI Scam Risk Scoring',
    description: 'Fast, structured evaluation of suspicious texts, URLs, and calls before you act.'
  },
  {
    title: 'Fraud Pattern Detection',
    description: 'Flags urgency, financial pressure, phishing links, impersonation, and credential theft patterns.'
  },
  {
    title: 'Safe Action Guidance',
    description: 'Tells you what to avoid, what to verify, and what to report.'
  }
];

const scamTypes = [
  'SMS messages',
  'Emails',
  'WhatsApp-style chats',
  'Web URLs',
  'Phone numbers',
  'Investment offers',
  'Job scams',
  'Banking and OTP requests'
];

const stats = [
  { label: 'Scam categories covered', value: '14+' },
  { label: 'Suspicious indicators tracked', value: '50+' },
  { label: 'Protection guidance', value: '24/7' }
];

const emptyResult = {
  suspicious: false,
  riskLevel: 'Low',
  riskScore: 0,
  scamCategory: 'Low-risk inquiry',
  suspiciousIndicators: ['No strong scam indicators found'],
  explanation: 'Add content to run an analysis.',
  recommendedActions: ['Verify independently before taking action.'],
  disclaimer: 'ScamGuard AI is an educational decision-support tool.'
};

function LandingPage() {
  return (
    <div>
      <header className="site-header">
        <div className="container nav">
          <div className="brand">
            <div className="brand-mark">S</div>
            <div>
              <div className="brand-name">ScamGuard AI</div>
              <small>Detect. Understand. Stay Safe.</small>
            </div>
          </div>
          <nav className="nav-links">
            <a href="#features">Features</a>
            <a href="#how-it-works">How It Works</a>
            <a href="#categories">Categories</a>
            <a href="#safety-tips">Safety Tips</a>
            <Link to="/analyzer" className="primary-button">Analyze Suspicious Content</Link>
          </nav>
        </div>
      </header>

      <main>
        <section className="hero">
          <div className="container hero-grid">
            <div>
              <span className="eyebrow">Cybersecurity protection powered by AI</span>
              <h1>Think It's a Scam? Let ScamGuard AI Check It.</h1>
              <p>
                ScamGuard AI helps you evaluate suspicious messages, URLs, and calls to understand the risk, the likely scam pattern,
                and the safest next action.
              </p>
              <div className="hero-actions">
                <Link to="/analyzer" className="primary-button large">Analyze Suspicious Content</Link>
                <a href="#features" className="secondary-button">Explore Features</a>
              </div>
              <div className="mini-stats">
                {stats.map((stat) => (
                  <div key={stat.label} className="mini-stat">
                    <strong>{stat.value}</strong>
                    <span>{stat.label}</span>
                  </div>
                ))}
              </div>
            </div>
            <div className="hero-card">
              <div className="status-pill">Live detection overview</div>
              <h3>Urgent request detected</h3>
              <ul>
                <li>Pressure tactics</li>
                <li>Refund or money request</li>
                <li>Suspicious link or link shortening</li>
                <li>Impersonation of a trusted contact</li>
              </ul>
              <div className="risk-badges">
                <span className="risk-badge danger">High Risk</span>
                <span className="risk-badge warning">Moderate confidence</span>
              </div>
            </div>
          </div>
        </section>

        <section className="section" id="features">
          <div className="container">
            <div className="section-heading">
              <span className="eyebrow">Why people use ScamGuard AI</span>
              <h2>Built to spot scams before risk turns into loss</h2>
            </div>
            <div className="cards-grid">
              {features.map((feature) => (
                <div key={feature.title} className="info-card">
                  <div className="card-icon">✓</div>
                  <h3>{feature.title}</h3>
                  <p>{feature.description}</p>
                </div>
              ))}
            </div>
          </div>
        </section>

        <section className="section alt-bg" id="how-it-works">
          <div className="container">
            <div className="section-heading">
              <span className="eyebrow">Simple workflow</span>
              <h2>How it works</h2>
            </div>
            <div className="steps-grid">
              <div className="step">
                <span>01</span>
                <h3>Share the message</h3>
                <p>Paste a suspicious SMS, email, chat, URL, or phone number into the analyzer.</p>
              </div>
              <div className="step">
                <span>02</span>
                <h3>AI pattern check</h3>
                <p>ScamGuard AI reviews urgency, financial intent, fake links, impersonation, and credential requests.</p>
              </div>
              <div className="step">
                <span>03</span>
                <h3>Review safe guidance</h3>
                <p>See the likely scam category, risk score, and recommended actions to protect yourself.</p>
              </div>
            </div>
          </div>
        </section>

        <section className="section" id="categories">
          <div className="container">
            <div className="section-heading">
              <span className="eyebrow">Common scam patterns</span>
              <h2>Fraud categories we cover</h2>
            </div>
            <div className="tag-list">
              {scamTypes.map((type) => (
                <span key={type} className="tag">{type}</span>
              ))}
            </div>
          </div>
        </section>

        <section className="section alt-bg" id="safety-tips">
          <div className="container safety-layout">
            <div>
              <span className="eyebrow">Safety reminders</span>
              <h2>Quick protection tips</h2>
            </div>
            <div className="tips-list">
              <div className="tip-item">
                <strong>Never share OTPs</strong>
                <p>Legitimate teams do not ask for one-time passwords or banking credentials over chat.</p>
              </div>
              <div className="tip-item">
                <strong>Check the sender</strong>
                <p>Verify whether the sender is a trusted institution, a known contact, or an official domain.</p>
              </div>
              <div className="tip-item">
                <strong>Pause before acting</strong>
                <p>Urgency and threats are common pressure tactics used by scammers.</p>
              </div>
            </div>
          </div>
        </section>
      </main>

      <footer className="site-footer">
        <div className="container footer-grid">
          <div>
            <div className="brand-name">ScamGuard AI</div>
            <p>Detect. Understand. Stay Safe.</p>
          </div>
          <div>
            <h4>Need help?</h4>
            <p>Use the analyzer to review suspicious content before taking action.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}

function AnalyzerPage() {
  const [analysisType, setAnalysisType] = useState('TEXT');
  const [message, setMessage] = useState('');
  const [url, setUrl] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [result, setResult] = useState(emptyResult);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      const payload = {
        analysisType,
        message,
        url,
        phoneNumber
      };
      const response = await axios.post('http://localhost:8080/api/scams/analyze', payload);
      const payloadResult = response.data?.data ?? response.data;
      setResult(payloadResult || emptyResult);
    } catch (err) {
      setError(err.response?.data?.message || 'Unable to reach the ScamGuard AI service. Please confirm the backend is running.');
      setResult(emptyResult);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="analyzer-page">
      <header className="site-header compact">
        <div className="container nav">
          <div className="brand">
            <div className="brand-mark">S</div>
            <div>
              <div className="brand-name">ScamGuard AI</div>
              <small>Detect. Understand. Stay Safe.</small>
            </div>
          </div>
          <nav className="nav-links">
            <Link to="/">Home</Link>
            <Link to="/analyzer" className="primary-button">Open Analyzer</Link>
          </nav>
        </div>
      </header>

      <div className="container analyzer-layout">
        <form className="analysis-form" onSubmit={handleSubmit}>
          <div className="section-heading left">
            <span className="eyebrow">Scam analysis</span>
            <h2>Check suspicious content</h2>
          </div>

          <label>
            Analysis type
            <select value={analysisType} onChange={(event) => setAnalysisType(event.target.value)}>
              <option value="TEXT">Text / Message</option>
              <option value="URL">URL</option>
              <option value="PHONE">Phone number</option>
              <option value="COMBINED">Combined analysis</option>
            </select>
          </label>

          <label>
            Message or suspicious content
            <textarea
              value={message}
              onChange={(event) => setMessage(event.target.value)}
              placeholder="Paste the message, SMS, email, or WhatsApp chat here"
              rows="7"
            />
          </label>

          <label>
            URL
            <input
              type="text"
              value={url}
              onChange={(event) => setUrl(event.target.value)}
              placeholder="https://example-suspicious-site.com"
            />
          </label>

          <label>
            Phone number
            <input
              type="text"
              value={phoneNumber}
              onChange={(event) => setPhoneNumber(event.target.value)}
              placeholder="+91 98765 43210"
            />
          </label>

          <button type="submit" className="primary-button large" disabled={isLoading}>
            {isLoading ? 'Analyzing...' : 'Analyze'}
          </button>

          {error && <div className="error-box">{error}</div>}
        </form>

        <aside className="result-panel">
          <div className="result-header">
            <span className="eyebrow">Assessment</span>
            <h3>{result.riskLevel} risk</h3>
          </div>
          <div className="score-box">
            <span className="score-value">{result.riskScore}</span>
            <span className="score-label">Risk score</span>
          </div>

          <div className="result-meta">
            <div>
              <small>Status</small>
              <strong>{result.suspicious ? 'Suspicious' : 'Low suspicion'}</strong>
            </div>
            <div>
              <small>Category</small>
              <strong>{result.scamCategory}</strong>
            </div>
          </div>

          <div className="result-block">
            <h4>Why it may be a scam</h4>
            <p>{result.explanation}</p>
          </div>

          <div className="result-block">
            <h4>Suspicious indicators</h4>
            <ul>
              {result.suspiciousIndicators?.map((item) => (
                <li key={item}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="result-block">
            <h4>Recommended actions</h4>
            <ul>
              {result.recommendedActions?.map((item) => (
                <li key={item}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="result-disclaimer">{result.disclaimer}</div>
        </aside>
      </div>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/analyzer" element={<AnalyzerPage />} />
      </Routes>
    </BrowserRouter>
  );
}
