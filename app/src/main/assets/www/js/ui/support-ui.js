G.SupportUI = (() => {
  const $ = G.UI.$;
  const F = G.Funding;
  function init() {
    const b = $('btn-support');
    if (!b) return;
    b.classList.remove('hidden');
    b.innerHTML = G.Icons.html('heart', 18) + ' <span>' + (G.I18n.lang === 'en' ? 'Support' : 'Apoyar') + '</span>';
    b.title = G.I18n.lang === 'en' ? 'Support this port voluntarily' : 'Apoyar voluntariamente este port';
    b.onclick = open;
    const closeButton = $('sup-close');
    if (closeButton) closeButton.onclick = close;
  }
  function open() {
    const en = G.I18n.lang === 'en';
    $('sup-body').innerHTML = `<p>${en ? F.messageEn : F.messageEs}</p>
      <p class="sub">${en ? 'Support is completely optional. You choose the amount on PayPal; it does not unlock anything in the game.' : 'El apoyo es totalmente voluntario. Tú eliges el monto en PayPal; no desbloquea nada en el juego.'}</p>
      <div class="row" style="justify-content:center;margin-top:14px">
        <button class="btn gold" id="sup-paypal">${en ? 'Continue to PayPal' : 'Continuar a PayPal'}</button>
      </div>`;
    $('scr-support').classList.remove('hidden');
    $('sup-paypal').onclick = () => {
      if (window.Android && typeof window.Android.openPayPal === 'function') window.Android.openPayPal(F.url);
      else window.open(F.url, '_blank', 'noopener,noreferrer');
    };
  }
  function close() { $('scr-support').classList.add('hidden'); }
  return { init, open, close };
})();
