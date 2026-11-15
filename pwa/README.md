# Cotrico — Cahier des charges fonctionnel et technique

**Version :** 1.0  
**Type de produit :** Application web mobile installable (PWA)  
**Objectif :** Simplifier la gestion des relevés et la facturation mensuelle de plusieurs immeubles.

---

# 1. Présentation du projet

## 1.1. Contexte

Cotrico est une application destinée à faciliter le travail quotidien d'un gestionnaire immobilier qui doit :

- Gérer plusieurs immeubles.
- Enregistrer les logements et leurs occupants.
- Relever chaque mois les compteurs électriques des locataires.
- Calculer les consommations d'électricité.
- Ajouter automatiquement les frais fixes d'eau.
- Générer une facture PDF pour chaque logement occupé.
- Partager directement les factures avec les locataires.

L'application est conçue pour un usage personnel et familial. Elle n'est pas destinée à devenir un SaaS commercial.

## 1.2. Objectif principal

Remplacer les calculs manuels et la production manuelle des factures par un processus simple, rapide et fiable, utilisable principalement depuis un téléphone.

## 1.3. Principes de conception

L'agent IA doit respecter les principes suivants :

1. **Simplicité avant tout :** ne développer aucune fonctionnalité qui ne répond pas à un besoin réel du gestionnaire.
2. **Mobile-first :** toutes les opérations principales doivent être faciles à effectuer sur un téléphone.
3. **Peu de manipulations :** automatiser les calculs et les informations répétitives.
4. **Saisie flexible :** permettre la saisie manuelle même lorsque l'OCR est disponible.
5. **Pas de fonctionnalités SaaS :** ne pas développer de gestion d'abonnements, de rôles complexes, de facturation commerciale ou de fonctionnalités multi-clients.
6. **Fiabilité des données :** préserver l'historique des relevés et des factures.
7. **Fonctionnement hors connexion :** permettre de continuer les relevés même en l'absence de réseau.

---

# 2. Périmètre fonctionnel

L'application comporte uniquement les modules suivants :

- Gestion des immeubles.
- Gestion des logements et locataires.
- Configuration des tarifs.
- Relevés des compteurs électriques avec OCR.
- Calcul et génération des factures.
- Consultation de l'historique des factures.

Aucun module de gestion des loyers n'est prévu dans le MVP.

# 3. Gestion des immeubles

Le gestionnaire doit pouvoir :

- Ajouter un immeuble.
- Modifier les informations d'un immeuble.
- Consulter ses logements.
- Consulter le nombre de logements occupés et vacants.

Informations d'un immeuble :

- Nom ou identifiant.
- Adresse (facultative).
- Description (facultative).

# 4. Gestion des logements

Chaque immeuble contient plusieurs logements.

Informations d'un logement :

- Numéro ou identifiant du logement.
- Type de logement (studio, chambre, appartement, etc.).
- Statut : occupé ou vacant.
- Montant fixe mensuel de l'eau, déterminé par le type de logement.
- Informations du locataire actuel.

Le gestionnaire doit pouvoir :

- Ajouter et modifier un logement.
- Enregistrer un locataire.
- Modifier les informations du locataire.
- Enregistrer le départ d'un locataire.
- Consulter l'historique d'occupation du logement.

Informations minimales du locataire :

- Nom complet.
- Numéro de téléphone (facultatif).

Un logement vacant ne doit pas être inclus dans la facturation ordinaire tant qu'il n'est pas occupé.

# 5. Configuration des tarifs

Les tarifs doivent être configurés une seule fois et réutilisés automatiquement.

## 5.1. Électricité

Un tarif unique du kilowattheure (kWh) est appliqué à tous les logements.

Le gestionnaire peut modifier ce tarif.

## 5.2. Eau

Le montant de l'eau est fixe chaque mois et dépend du type de logement.

Exemple :

| Type de logement | Frais d'eau |
|---|---:|
| Chambre | Tarif configurable |
| Studio | Tarif configurable |
| Appartement | Tarif configurable |

Les montants sont configurables par le gestionnaire.

Les tarifs utilisés doivent être enregistrés dans chaque facture afin qu'une modification ultérieure ne change jamais les anciennes factures.

# 6. Relevés des compteurs électriques

## 6.1. Fonctionnement

Chaque logement occupé possède un compteur électrique individuel.

Pour chaque période de facturation, le gestionnaire doit enregistrer le nouvel index du compteur.

L'ancien index est automatiquement récupéré depuis le dernier relevé validé.

Le gestionnaire ne doit normalement saisir que le nouvel index.

## 6.2. Modes de saisie

Deux modes doivent être disponibles :

**A. Saisie manuelle**

- Afficher le logement et le locataire.
- Afficher l'ancien index.
- Permettre la saisie du nouvel index.
- Calculer automatiquement la consommation.

**B. Lecture par OCR**

- Permettre de prendre une photo du compteur ou de sélectionner une image.
- Extraire automatiquement l'index détecté.
- Afficher la valeur reconnue avant validation.
- Permettre de corriger manuellement la valeur.
- Ne jamais valider automatiquement une lecture OCR sans confirmation du gestionnaire.

L'OCR doit être une aide à la saisie, et non une obligation.

## 6.3. Validation des relevés

Avant validation :

- Le nouvel index doit être supérieur ou égal à l'ancien index.
- Une valeur invalide doit être signalée.
- Le gestionnaire doit pouvoir corriger la valeur.
- Un relevé validé ne doit pas être remplacé silencieusement.

En cas d'erreur sur un relevé déjà validé, prévoir une action explicite de correction.

## 6.4. Calcul de consommation

Formule :

`Consommation = Nouvel index - Ancien index`

Exemple :

- Ancien index : 1 250 kWh.
- Nouvel index : 1 390 kWh.
- Consommation : 140 kWh.

# 7. Facturation mensuelle

## 7.1. Création d'une période

Le gestionnaire sélectionne :

- Le mois.
- L'année.
- L'immeuble à traiter.

L'application affiche les logements occupés et leur état de relevé.

## 7.2. État des relevés

Chaque logement doit avoir un état clairement identifiable :

- À relever.
- Relevé effectué.
- Relevé à corriger.
- Facture générée.

Le gestionnaire doit pouvoir identifier rapidement les logements qui restent à traiter.

## 7.3. Calcul de la facture

La facture comprend deux éléments :

**Électricité**

`Consommation × Tarif du kWh`

**Eau**

`Montant fixe correspondant au type de logement`

**Total**

`Montant électricité + Montant eau`

Exemple :

- Consommation : 140 kWh.
- Tarif : 100 FCFA/kWh.
- Électricité : 14 000 FCFA.
- Eau : 2 000 FCFA.
- Total : 16 000 FCFA.

Les chiffres sont uniquement illustratifs.

## 7.4. Génération du PDF

Une facture doit être générée pour chaque logement concerné.

Elle doit contenir :

- Nom ou identité du gestionnaire.
- Nom de l'immeuble.
- Identifiant du logement.
- Nom du locataire.
- Période de facturation.
- Ancien index.
- Nouvel index.
- Consommation électrique.
- Tarif du kWh.
- Montant de l'électricité.
- Montant de l'eau.
- Montant total à payer.
- Date de génération.
- Référence unique de la facture.

Le document doit être lisible sur téléphone et adapté à l'impression.

## 7.5. Partage des factures

Après la génération du PDF :

1. L'application ouvre directement la fenêtre de partage native du téléphone ou du navigateur.
2. Le gestionnaire sélectionne WhatsApp ou une autre application.
3. Il choisit lui-même le destinataire.
4. Il partage le document PDF.

Utiliser la Web Share API lorsque le navigateur la prend en charge, avec partage de fichiers si disponible.

Si le partage natif n'est pas disponible, proposer le téléchargement ou l'ouverture du PDF.

Il n'est pas nécessaire d'intégrer une API WhatsApp.

# 8. Historique des factures

Le gestionnaire doit pouvoir :

- Consulter les factures d'un mois donné.
- Filtrer par immeuble.
- Retrouver les factures d'un logement.
- Ouvrir ou télécharger un ancien PDF.
- Consulter les anciens relevés.

Une facture déjà générée doit conserver les montants et tarifs de sa création.

Le système doit empêcher la génération accidentelle de plusieurs factures pour le même logement et la même période.

Une éventuelle régénération doit être une action explicite.

# 9. Écrans de l'application

L'interface doit rester très réduite.

## 9.1. Accueil

Afficher uniquement :

- Les immeubles.
- Le nombre de logements occupés.
- Le nombre de relevés restant à effectuer pour la période sélectionnée.
- Un accès rapide à la facturation du mois.

Éviter les graphiques et statistiques décoratifs.

## 9.2. Immeubles

Liste des immeubles avec accès à leurs logements.

## 9.3. Logements

Liste des logements d'un immeuble avec :

- Identifiant du logement.
- Nom du locataire.
- Type de logement.
- Statut d'occupation.

## 9.4. Relevés

Écran principal du travail mensuel :

- Sélection du mois et de l'immeuble.
- Liste des logements occupés.
- Ancien index.
- Nouvel index.
- Bouton de saisie manuelle.
- Bouton de lecture OCR.
- État du relevé.

L'interface doit permettre de traiter les logements les uns après les autres rapidement.

## 9.5. Factures

Liste des factures générées avec accès aux PDF.

## 9.6. Paramètres

Contenir uniquement :

- Tarif du kWh.
- Tarifs fixes de l'eau par type de logement.
- Informations du gestionnaire utilisées sur les factures.

---

# 10. Fonctionnement hors connexion

L'application doit être installable comme une PWA.

Les opérations suivantes doivent fonctionner hors connexion :

- Consulter les immeubles et logements déjà synchronisés.
- Consulter les anciens index.
- Effectuer une saisie manuelle.
- Enregistrer temporairement les relevés.
- Retrouver les relevés en attente de synchronisation.

Les données doivent être stockées localement avec IndexedDB.

Lorsqu'une connexion est disponible, l'application synchronise les modifications avec le serveur.

L'interface doit indiquer clairement si les données sont synchronisées ou en attente.

L'OCR peut nécessiter une connexion selon le moteur choisi. Si le traitement OCR est indisponible hors connexion, la saisie manuelle doit rester immédiatement accessible.

---

# 11. Règles métier et cas particuliers

L'agent IA doit prendre en compte les règles suivantes :

1. Un compteur appartient à un logement.
2. Un logement ne doit avoir qu'un relevé validé par période.
3. L'ancien index d'une période provient du dernier relevé validé.
4. Une consommation négative est interdite.
5. Les tarifs sont enregistrés dans chaque facture.
6. Les anciens relevés et factures doivent être conservés.
7. Le départ d'un locataire ne doit pas supprimer son historique.
8. Une erreur OCR ne doit jamais produire automatiquement une facture.
9. Les logements vacants sont exclus de la facturation ordinaire.
10. Une facture générée ne doit pas être modifiée silencieusement.
11. Les opérations hors connexion ne doivent pas créer de doublons lors de la synchronisation.

Prévoir également des mécanismes explicites pour les situations suivantes :

- Compteur remplacé ou réinitialisé.
- Relevé manquant.
- Correction d'un index.
- Changement de locataire.
- Changement de tarif.
- Relevé effectué plusieurs fois par erreur.

Ne pas inventer de règles de facturation pour ces cas : lorsqu'une décision métier est nécessaire, la rendre configurable ou demander confirmation.

---

# 12. Architecture technique recommandée

L'agent IA peut adapter les choix techniques, mais doit privilégier une architecture simple et maintenable.

## Frontend

- Vue 3.
- TypeScript.
- Vite.
- Tailwind CSS.
- PWA.
- IndexedDB pour les données locales.
- TanStack Query pour les données distantes et la synchronisation.

## Backend

Un backend léger peut être utilisé pour :

- Conserver les données de manière centralisée.
- Synchroniser les relevés.
- Gérer les immeubles, logements et locataires.
- Générer et conserver les factures.
- Centraliser les règles de calcul.

NestJS est une option adaptée.

## Base de données

PostgreSQL.

## OCR

Choisir une solution OCR capable de lire les chiffres des compteurs électriques à partir de photos.

Le moteur OCR doit être isolé derrière un service afin de pouvoir être remplacé sans modifier le fonctionnement des écrans.

## PDF

Utiliser une bibliothèque adaptée à la génération de documents PDF.

La génération doit produire un document stable, avec une référence unique.

---

# 13. Modèle de données indicatif

L'agent IA doit proposer un modèle cohérent avec les règles métier.

Entités principales :

- `Building` : immeuble.
- `Apartment` : logement.
- `Tenant` : locataire.
- `Occupancy` : historique d'occupation.
- `ElectricityMeter` : compteur électrique.
- `MeterReading` : relevé d'index.
- `BillingPeriod` : période de facturation.
- `Invoice` : facture.
- `BillingConfiguration` : configuration des tarifs.

Les factures doivent conserver une copie des informations et tarifs nécessaires à leur consultation historique, indépendamment des modifications futures.

---

# 14. Sécurité et fiabilité

- Protéger l'accès à l'application par une authentification simple.
- Ne pas exposer les données des locataires.
- Prévoir une sauvegarde des données.
- Éviter les pertes de données lors d'une fermeture ou d'une actualisation de l'application.
- Prévoir une synchronisation idempotente.
- Vérifier les calculs côté serveur avant de confirmer une facture.
- Éviter les suppressions définitives des relevés et factures.

---

# 15. Priorités du MVP

Le MVP doit être utilisable de bout en bout dès sa première version.

### Priorité 1 — Fondations

- Installation PWA.
- Authentification simple.
- Gestion des immeubles.
- Gestion des logements.
- Gestion des locataires.
- Configuration des tarifs.

### Priorité 2 — Relevés

- Saisie manuelle.
- Capture ou import de photo.
- OCR.
- Correction de la valeur reconnue.
- Validation des relevés.
- Sauvegarde hors connexion.

### Priorité 3 — Facturation

- Calcul automatique.
- Génération des PDF.
- Historique des factures.
- Ouverture de la fenêtre de partage après génération.

### Priorité 4 — Fiabilité

- Synchronisation.
- Gestion des erreurs.
- Protection contre les doublons.
- Sauvegarde des données.
- Tests des calculs et règles métier.

---

# 16. Critères d'acceptation

Le MVP est considéré comme fonctionnel lorsque le gestionnaire peut :

1. Créer plusieurs immeubles.
2. Ajouter plusieurs logements dans chaque immeuble.
3. Associer des locataires aux logements.
4. Configurer les tarifs de l'électricité et de l'eau.
5. Effectuer un relevé manuellement.
6. Prendre une photo d'un compteur et obtenir une proposition de lecture OCR.
7. Corriger une lecture OCR avant validation.
8. Retrouver automatiquement l'ancien index.
9. Calculer la consommation et le montant à payer.
10. Générer une facture PDF correcte.
11. Ouvrir directement la fenêtre de partage après génération.
12. Retrouver une ancienne facture.
13. Effectuer des relevés sans connexion et les synchroniser ultérieurement.

---

# 17. Instructions impératives à l'agent IA

Tu es responsable de concevoir et développer Cotrico conformément à ce cahier des charges.

Respecte impérativement les consignes suivantes :

- Ne transforme pas Cotrico en SaaS.
- Ne développe pas de gestion multi-entreprises ou multi-clients.
- Ne développe pas de système complexe de permissions.
- Ne développe pas de gestion des loyers.
- N'ajoute pas de fonctionnalités non demandées.
- Ne surcharge pas les écrans.
- Ne multiplie pas les tableaux de bord, graphiques et indicateurs.
- Privilégie les actions directes et les formulaires courts.
- Optimise chaque écran pour une utilisation sur téléphone.
- Préserve la simplicité du parcours de relevé et de facturation.
- Ne considère jamais une valeur OCR comme validée sans confirmation humaine.
- Ne sacrifie pas la fiabilité des calculs à la simplicité de l'interface.
- Ne crée pas d'abstractions ou de couches techniques inutiles.
- Ne modifie pas les choix d'architecture sans raison technique valable.

Avant chaque développement :

1. Comprendre le besoin fonctionnel.
2. Identifier les écrans et composants concernés.
3. Vérifier les règles métier existantes.
4. Implémenter uniquement ce qui est nécessaire.
5. Tester le comportement principal et les cas particuliers.

**Règle fondamentale : si une fonctionnalité n'aide pas directement le gestionnaire à gérer ses logements, relever les compteurs, calculer les factures ou les partager, elle ne doit pas être développée.**